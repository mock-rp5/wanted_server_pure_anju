package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



public class UserDto {

    @Builder
    @Data
    @AllArgsConstructor
    public static class PostUserRes{
        private String jwt;
        private Long userId;
    }

    @Builder
    @Data
    @AllArgsConstructor
    public static class PostLoginUserRes{
        private String jwt;
        private Long userId;
    }

    @Builder
    @Data
    public static class GetUserRes{
        private Long userIdx;
        private String jwt;
        private String profileImage;
        private String name;
        private String email;
        private String phoneNumber;
        private Boolean pushNotification;
    }
}
