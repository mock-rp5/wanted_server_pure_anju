package com.example.demo.src.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserRes {
    private Long userIdx;
    private String jwt;
    private String profileImage;
    private String name;
    private String email;
    private String phoneNumber;
    private Boolean pushNotification;

}
