package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Data
public class PostUserReq {

    @NotBlank(message = "프로필 사진을 입력해 주세요. 없다면 기본 값으로 넣어주세요.")
    private String profileImage;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "이름의 형식을 확인해 주세요.")
    private String name;

    @NotBlank(message = "휴대번호는 -를 제외한 11자리를 입력해 주세요.")
    private String phoneNumber;

    @NotBlank(message = "패스워드에 공백이 포함되어 있는지 확인해 주세요.")
    private String password;

    @NotNull(message = "개인정보 동의 체크를 해주세요.")
    private Boolean privacyPolicyAgreement;
    @NotNull
    private Boolean pushNotification;

    @NotNull(message = "직군을 선택해 주세요.")
    private String jobGroup;

    @NotNull(message = "직무를 선택해 주세요")
    private String duty;

    @NotNull(message = "경력을 선택해 주세요.")
    private int experience;


    private String schoolName;
    private String companyName;

}
