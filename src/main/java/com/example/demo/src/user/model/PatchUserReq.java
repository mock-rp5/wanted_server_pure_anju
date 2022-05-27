package com.example.demo.src.user.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PatchUserReq {


    @Data
    public static class PatchUserProfileImageReq{
        @NotBlank(message = "프로필 이미지를 넣어주세요")
        private String profileImage;
    }
}
