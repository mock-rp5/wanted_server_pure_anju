package com.example.demo.src.validation.model.email;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostEmailReq {
    private String email;
}
