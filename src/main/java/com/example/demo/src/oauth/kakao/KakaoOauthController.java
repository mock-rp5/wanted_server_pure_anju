package com.example.demo.src.oauth.kakao;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.OAUTH_KAKAO_EMPTY_TOKEN;

@RestController
@RequestMapping("/app/oauth/kakao")
public class KakaoOauthController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final KakaoOauthService kakaoOauthService;

    public KakaoOauthController(KakaoOauthService kakaoOauthService){
        this.kakaoOauthService = kakaoOauthService;
    }

    /**
     * 카카오 OAuth 로그인(해당 주소로 인가토큰 전달)
     * [POST]
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostLoginRes> kakaoLogin(@RequestBody PostKakaoLoginReq postKakaoLoginReq) {
        if (postKakaoLoginReq.getAccessToken() == null || postKakaoLoginReq.getAccessToken().isEmpty()) {
            return new BaseResponse<>(OAUTH_KAKAO_EMPTY_TOKEN);
        }
        try {
            // 액세스 토큰으로 사용자 정보 받아온다.
            KakaoUserInfo kaKaoUserInfo = KakaoOauthService.getKakaoUserInfo(postKakaoLoginReq.getAccessToken());

            // 로그인 처리 진행 후 jwt, userId 반환
            PostLoginRes postLoginRes = kakaoOauthService.kakaoLogin(kaKaoUserInfo);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
