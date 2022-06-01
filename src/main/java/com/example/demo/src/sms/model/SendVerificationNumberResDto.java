package com.example.demo.src.sms.model;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendVerificationNumberResDto {
    private String verificationNumber;
    private String statusCode;
    private String statusName;
    private String requestId;
    private String requestTime;
}
