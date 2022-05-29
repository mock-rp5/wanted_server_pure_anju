package com.example.demo.src.user.model;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class PatchUserReq {


    @Data
    public static class PatchUserProfileImageReq{
        @NotBlank(message = "프로필 이미지를 넣어주세요")
        private String profileImage;
    }

    @Data
    public static class PatchUserBasicInformationReq{

        @NotBlank(message = "이름의 형식을 확인해 주세요.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "이름의 형식을 확인해 주세요.")
        private String name;

        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @NotBlank(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "휴대번호는 -를 제외한 11자리를 입력해 주세요.")
        @Size(min = 11, max = 11)
        private String phoneNumber;
    }
}
