package com.example.demo.src.sms;

import java.io.UnsupportedEncodingException;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


import org.springframework.core.env.Environment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;


import com.example.demo.src.sms.model.SendVerificationNumberResDto;

import com.example.demo.src.sms.model.VerificationReqDto;
import com.fasterxml.jackson.core.JsonProcessingException;





@RestController
@RequestMapping("/app/sms")
public class SmsController {


    private final Environment env;
    private final SmsService smsService;

    public SmsController(Environment env, SmsService smsService) {
        this.env = env;
        this.smsService = smsService;
    }

    @ResponseBody
    @PostMapping("/verification-number")
    public BaseResponse<SendVerificationNumberResDto> smsVerificationNumberService(@RequestBody
                                                                                           VerificationReqDto verificationReqDto) throws UnsupportedEncodingException,
            NoSuchAlgorithmException,
            URISyntaxException,
            InvalidKeyException,
            JsonProcessingException {
        try {
            return new BaseResponse<>(smsService.sendVerificationNumber(verificationReqDto.getTo()));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
