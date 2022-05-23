package com.example.demo.src.user.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class PostLoginReq {
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @NotBlank(message = "패스워드에 공백이 포함되어 있는지 확인해 주세요.")
    private String password;

}
