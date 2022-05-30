package com.example.demo.src.validation.model.sms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PostSmsReq {

    @NotBlank(message = "휴대번호는 -를 제외한 11자리를 입력해 주세요.")
    @Size(min = 11, max = 11)
    private String phoneNumber;
}
