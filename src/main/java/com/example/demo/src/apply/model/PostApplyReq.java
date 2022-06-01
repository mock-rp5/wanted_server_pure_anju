package com.example.demo.src.apply.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostApplyReq {

    @NotBlank(message = "이름을 입력해 주세요.이름에 특수문자가 포함될 수 없습니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "이름을 입력해 주세요.이름에 특수문자가 포함될 수 없습니다.")
    private String name;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "휴대번호는 -를 제외한 11자리를 입력해 주세요.")
    @Size(min = 11, max = 11)
    private String phoneNumber;

    private String recommender;
    private int resumeIdx;
    private String status;
}

