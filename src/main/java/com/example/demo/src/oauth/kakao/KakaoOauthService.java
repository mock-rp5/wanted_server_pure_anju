package com.example.demo.src.oauth.kakao;

import com.example.demo.config.BaseException;
import com.example.demo.src.user.UserDao;
import com.example.demo.utils.JwtService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class KakaoOauthService {


    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public KakaoOauthService(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!
    }

    public static KakaoUserInfo getKakaoUserInfo(String accessToken) throws BaseException {
        String api = "https://kapi.kakao.com/v2/user/me";
        String authorization_header = "Bearer " + accessToken;
        KakaoUserInfo kakaoUserInfo;
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(api);
            HttpsURLConnection http = (HttpsURLConnection) url.openConnection();
            http.setRequestProperty("Authorization", authorization_header);
            http.setRequestMethod("GET");
            http.connect();

            int responseCode = http.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            InputStreamReader in = new InputStreamReader(http.getInputStream(), "utf-8");
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonObjectMain;

            jsonObjectMain = (JSONObject) parser.parse(sb.toString());
            JSONObject kakao_account = (JSONObject) jsonObjectMain.get("kakao_account");
            boolean has_email = (boolean) kakao_account.get("has_email");
            JSONObject profile = (JSONObject) kakao_account.get("profile");
            String nickname = (String) profile.get("nickname");
            String email = (String) kakao_account.get("email");

            //System.out.println("nickname : " + nickname + ", has_email : " + has_email +  ", email : " + email);
            br.close();
            in.close();
            http.disconnect();
            kakaoUserInfo = new KakaoUserInfo(email, nickname);
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_KAKAO_OAUTH);
        }

        if (kakaoUserInfo.getEmail() == null) {
            throw new BaseException(FAILED_TO_KAKAO_EMAIL);
        } else {
            return kakaoUserInfo;
        }
    }

    public PostLoginRes kakaoLogin(KakaoUserInfo kakaoUserInfo) throws BaseException{
        try{
            GetKakaoUserRes getKakaoUserRes = userDao.getUserByEmail(kakaoUserInfo.getEmail());
            Long userIdx = getKakaoUserRes.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(userIdx, jwt);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }



    }
}
