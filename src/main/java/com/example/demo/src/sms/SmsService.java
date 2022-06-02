package com.example.demo.src.sms;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.config.BaseException;

import com.example.demo.src.sms.model.MessagesReqDto;

import com.example.demo.src.sms.model.SendVerificationNumberResDto;
import com.example.demo.src.sms.model.SmsRequestDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.example.demo.config.BaseResponseStatus.MESSAGE_INVALID_PHONE_NUMBER;

@Service
public class SmsService {

    @Value("${ncp.access_key}")
    private String accessKey;

    @Value("${ncp.service_id}")
    private String serviceId;

    @Value("${ncp.secret_key}")
    private String secretKey;

    public SendVerificationNumberResDto sendVerificationNumber(String recipientPhoneNumber) throws
            JsonProcessingException,
            UnsupportedEncodingException,
            InvalidKeyException,
            NoSuchAlgorithmException,
            URISyntaxException, BaseException {

        int phoneNumberLength = recipientPhoneNumber.length();
        if (phoneNumberLength < 10 || 11 < phoneNumberLength) {
            throw new BaseException(MESSAGE_INVALID_PHONE_NUMBER);
        }

        RestTemplate restTemplate = new RestTemplate();

        // 4글자 인증번호
        String verificationNumber = makeRandomNumber();
        String verificationMessage = String.format("인증 번호 %s 를 입력하세요.", verificationNumber);

        // Long time = System.currentTimeMillis();
        String time = Long.toString(System.currentTimeMillis());
        List<MessagesReqDto> messages = new ArrayList<>();
        // 보내는 사람에게 내용을 보냄.
        messages.add(new MessagesReqDto(recipientPhoneNumber, verificationMessage)); // content부분이 내용임

        // 전체 json에 대해 메시지를 만든다.
        SmsRequestDto smsRequestDto = new SmsRequestDto("SMS", "COMM", "82", "01041680955", "MangoLtd", messages);

        // 쌓아온 바디를 json 형태로 변환시켜준다.
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(smsRequestDto);

        // 헤더에서 여러 설정값들을 잡아준다.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time);
        headers.set("x-ncp-iam-access-key", accessKey);

        // 제일 중요한 signature 서명하기.
        String sig = makeSignature(time);
        System.out.println("sig -> " + sig);
        headers.set("x-ncp-apigw-signature-v2", sig);

        // 위에서 조립한 jsonBody와 헤더를 조립한다.
        HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);
        System.out.println(body.getBody());

        // restTemplate로 post 요청을 보낸다. 별 일 없으면 202 코드 반환된다.
        SendVerificationNumberResDto sendVerificationNumberResDto = restTemplate.postForObject(
                new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"), body,
                SendVerificationNumberResDto.class);
        // 반환된 결과에 확인 번호를 추가하여 맵핑시킨다.
        Objects.requireNonNull(sendVerificationNumberResDto).setVerificationNumber(verificationNumber);

        return sendVerificationNumberResDto;
    }

    public String makeSignature(String time) throws
            UnsupportedEncodingException,
            InvalidKeyException,
            NoSuchAlgorithmException {

        String space = " ";                    // one space
        String newLine = "\n";                    // new line
        String method = "POST";                    // method
        String url = "/sms/v2/services/" + serviceId + "/messages";    // url (include query string)
        String timestamp = time;            // current timestamp (epoch)

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

        return encodeBase64String;
    }

    private String makeRandomNumber() {
        // It will generate 4 digit random Number.
        // from 0 to 9999
        Random rnd = new Random();
        int number = rnd.nextInt(9999);

        // this will convert any number sequence into 4 character.
        return String.format("%04d", number);
    }

}
