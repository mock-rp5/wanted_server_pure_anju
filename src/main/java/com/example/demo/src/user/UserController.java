package com.example.demo.src.user;

import com.example.demo.config.BaseResponseStatus;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/users")
@Slf4j
public class UserController {


    private final UserProvider userProvider;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }


    /**
     * 회원가입 API
     * [post] /app/users/
     */
    @PostMapping("")
    public BaseResponse<UserDto.PostUserRes> createUser(@Valid @RequestBody PostUserReq postUserReq) {
        try {
            return new BaseResponse<>(userService.createUser(postUserReq));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원정보 조회API
     * [get] /app/users/
     */

    @GetMapping("")
    public BaseResponse<UserDto.GetUserRes> retrieveUser() {
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(userProvider.retrieveUser(userIdx));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 로그인 API
     * [post] /app/users/login
     */
    @PostMapping("/sign-in")
    public BaseResponse<UserDto.PostLoginUserRes> logIn(@Valid @RequestBody PostLoginReq postLoginReq) {
        try {
            return new BaseResponse<>(userProvider.logIn(postLoginReq));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 탈퇴 API
     * [patch] /app/users/delete
     */
    @PatchMapping("/delete")
    public BaseResponse<BaseResponseStatus> deleteUser() {
        try {
            Long userIdx = jwtService.getUserIdx();
            userService.deleteUser(userIdx);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 프로필 이미지 변경 API
     * [patch] /app/users/profileImage
     */
    @PatchMapping("/profileImage")
    public BaseResponse<BaseResponseStatus> updateUserProfileImage(@Valid @RequestBody PatchUserReq.PatchUserProfileImageReq patchUserProfileImageReq) {
        try {
            Long userIdx = jwtService.getUserIdx();
            userService.updateUserProfileImage(userIdx, patchUserProfileImageReq);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


//
//    /**
//     * 유저정보변경 API
//     * [PATCH] /users/:userIdx
//     * @return BaseResponse<String>
//     */
//    @ResponseBody
//    @PatchMapping("/{userIdx}")
//    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody User user){
//        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
//            //같다면 유저네임 변경
//            PatchUserReq patchUserReq = new PatchUserReq(userIdx,user.getUserName());
//            userService.modifyUserName(patchUserReq);
//
//            String result = "";
//        return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }


}
