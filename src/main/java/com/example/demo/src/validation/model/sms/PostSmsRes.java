package com.example.demo.src.validation.model.sms;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostSmsRes {
    private LocalDateTime time;
    private String smsCode;
}
