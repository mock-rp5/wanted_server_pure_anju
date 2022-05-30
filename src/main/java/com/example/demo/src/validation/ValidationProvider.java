package com.example.demo.src.validation;

import com.example.demo.config.BaseException;
import com.example.demo.src.validation.model.email.PostEmailReq;
import com.example.demo.src.validation.model.sms.PostSmsReq;
import com.example.demo.src.validation.model.sms.PostSmsRes;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Slf4j
@Transactional(readOnly = true)
public class ValidationProvider {

    private final ValidationDao validationDao;

    @Autowired
    public ValidationProvider(ValidationDao validationDao) {
        this.validationDao = validationDao;
    }


    public void checkEmail(PostEmailReq postEmailReq) throws BaseException {
        if (validationDao.checkEmail(postEmailReq)) {
            throw new BaseException(DUPLICATED_EMAIL);
        }
    }

    public PostSmsRes certifiedPhoneNumber(PostSmsReq postSmsReq, String cerNum)  throws BaseException{

        String api_key = "NCSZXRCDVQSKSMUH";
        String api_secret = "PIFMUFUIHHPCE2JPZGPXWTWWZOTRX6O4";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", postSmsReq.getPhoneNumber());    // 수신전화번호
        params.put("from", "01097801480");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "라이징캠프 원티드 클론코딩 : 인증번호는" + "[" + cerNum + "]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
            return PostSmsRes.builder()
                    .time(LocalDateTime.now())
                    .smsCode(cerNum)
                    .build();
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

