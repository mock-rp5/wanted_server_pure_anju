package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostUserRes {
    private Long userIdx;
    private String jwt;
}
