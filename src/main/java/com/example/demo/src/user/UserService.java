package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.src.profile.ProfileDao;
import com.example.demo.src.profile.model.PutSpecializedFieldReq;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;


@Service
@Transactional(readOnly = false)
@Slf4j
public class UserService {

    private final UserDao userDao;
    private final ProfileDao profileDao;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserDao userDao, ProfileDao profileDao, JwtService jwtService) {
        this.userDao = userDao;
        this.profileDao = profileDao;
        this.jwtService = jwtService;
    }


    //POST
    public GetUserRes createUser(PostUserReq postUserReq) throws BaseException {

        String pwd;
        try {
            //암호화
            pwd = new SHA256().encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        Long userIdx = userDao.createUser(postUserReq);
        PutSpecializedFieldReq putSpecializedFieldReq = PutSpecializedFieldReq.builder()
                .userIdx(userIdx)
                .jobGroup(postUserReq.getJobGroup())
                .duty(postUserReq.getDuty())
                .experience(postUserReq.getExperience())
                .build();
        profileDao.modifyProfileSpecializedField(putSpecializedFieldReq);
        return userDao.getUser(userIdx);
    }


}
