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
    public UserDto.PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        String pwd;
        try {
            //암호화
            pwd = new SHA256().encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        Long userIdx = userDao.createUser(postUserReq);
        String jwt = jwtService.createJwt(userIdx);

        profileDao.modifyProfileSpecializedField(PutSpecializedFieldReq.builder()
                .userIdx(userIdx)
                .jobGroup(postUserReq.getJobGroup())
                .duty(postUserReq.getDuty())
                .experience(postUserReq.getExperience())
                .build());

        return new UserDto.PostUserRes(jwt, userIdx);
    }

    public void deleteUser(Long userIdx) throws BaseException {
        try {
            userDao.deleteUser(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void updateUserProfileImage(Long userIdx, PatchUserReq.PatchUserProfileImageReq patchUserProfileImageReq) throws BaseException {
        try {
            userDao.updateUserProfileImage(userIdx, patchUserProfileImageReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
