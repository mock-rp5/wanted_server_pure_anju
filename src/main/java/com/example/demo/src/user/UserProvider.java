package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.user.model.*;
import com.example.demo.src.validation.ValidationDao;
import com.example.demo.src.validation.ValidationProvider;
import com.example.demo.src.validation.model.email.PostEmailReq;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.example.demo.config.BaseResponseStatus.*;


@Slf4j
@Service
@Transactional(readOnly = true)

public class UserProvider {


    private final UserDao userDao;
    private final JwtService jwtService;
    private final ValidationDao validationDao;

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService, ValidationDao validationDao) {
        this.userDao = userDao;
        this.jwtService = jwtService;
        this.validationDao = validationDao;
    }

    // 회원 정보 조회
    public GetUserRes getUser(Long userIdx) throws BaseException {
        try {
            return userDao.getUser(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 로그인
    public GetUserRes logIn(PostLoginReq postLoginReq) throws BaseException {
        PostEmailReq postEmailReq = PostEmailReq.builder()
                .email(postLoginReq.getEmail())
                .build();

        if (validationDao.checkEmail(postEmailReq).equals(false)) {
            throw new BaseException(FAILED_TO_LOGIN);
        }

        User user = userDao.getPwd(postLoginReq);
        String encryptPwd;
        try {
            encryptPwd = new SHA256().encrypt(postLoginReq.getPassword());
            validationDao.checkEmail(postEmailReq);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if (user.getPassword().equals(encryptPwd)) {
            Long userIdx = user.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return userDao.getUser(userIdx);
        } else {
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }


}
