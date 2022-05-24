package com.example.demo.src.validation.model.email;


import lombok.*;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
public class PostEmailReq {
    @Email(message = "이메일 형식을 확인해 주세요")
    private String email;

    @Builder
    public PostEmailReq(String email) {
        this.email = email;
    }
}
