package com.example.demo.src.validation;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.validation.model.email.PostEmailReq;
import com.example.demo.src.validation.model.sms.PostSmsReq;
import com.example.demo.src.validation.model.sms.PostSmsRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Random;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@Slf4j
@RestController
@RequestMapping("/app/validation")
public class ValidationController {

    private final ValidationProvider validationProvider;

    @Autowired
    public ValidationController(ValidationProvider validationProvider) {
        this.validationProvider = validationProvider;
    }


    /**
     * 이메일 유효성 검증 API
     * [post] /app/validation
     */
    @PostMapping("/email")
    public BaseResponse<BaseResponseStatus> checkEmail(@Valid @RequestBody PostEmailReq postEmailReq) {
        if (postEmailReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        if (!isRegexEmail(postEmailReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try {
            validationProvider.checkEmail(postEmailReq);
            return new BaseResponse<>(AVAILABLE_EMAIL);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PostMapping("/sms")
    public BaseResponse<PostSmsRes> sendSMS(@Valid @RequestBody PostSmsReq postSmsReq) {
        try {
            Random rand = new Random();
            StringBuilder numStr = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                String ran = Integer.toString(rand.nextInt(10));
                numStr.append(ran);
            }

            System.out.println("수신자 번호 : " + postSmsReq.getPhoneNumber());
            System.out.println("인증번호 : " + numStr);
            return new BaseResponse<>(validationProvider.certifiedPhoneNumber(postSmsReq, numStr.toString()));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
