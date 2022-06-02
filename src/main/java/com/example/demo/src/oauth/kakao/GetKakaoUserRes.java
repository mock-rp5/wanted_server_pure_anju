package com.example.demo.src.oauth.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetKakaoUserRes {
    private Long userIdx;
    private String email;
    private String password;
}
