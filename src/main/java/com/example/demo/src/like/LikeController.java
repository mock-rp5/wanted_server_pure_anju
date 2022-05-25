package com.example.demo.src.like;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;

import com.example.demo.src.like.model.PostLikeReq;
import com.example.demo.src.like.model.PostLikeRes;
import com.example.demo.utils.JwtService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/likes")

public class LikeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final LikeProvider likeProvider;
    @Autowired
    private final LikeService likeService;
    @Autowired
    private final JwtService jwtService;

    public LikeController(LikeProvider likeProvider, LikeService likeService, JwtService jwtService) {
        this.likeProvider = likeProvider;
        this.likeService = likeService;
        this.jwtService = jwtService;
    }

    /**
     * 채용공고 좋아요 생성 API
     * [POST]
     */

    @ResponseBody
    @PostMapping("/employments/{employmentIdx}")
    public BaseResponse<PostLikeRes> createLike(@PathVariable("employmentIdx") int employmentIdx){


        try{
            Long userIdx = jwtService.getUserIdx();

            PostLikeRes postLikeRes = likeService.createLike(employmentIdx, userIdx);

            return new BaseResponse<>(postLikeRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
