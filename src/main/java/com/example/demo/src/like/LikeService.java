package com.example.demo.src.like;

import com.example.demo.config.BaseException;

import com.example.demo.src.like.model.PostLikeReq;
import com.example.demo.src.like.model.PostLikeRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.POST_LIKE_EXISTS;

@Service
public class LikeService {

    private final LikeProvider likeProvider;
    private final LikeDao likeDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public LikeService(LikeProvider likeProvider, LikeDao likeDao, JwtService jwtService) {
        this.likeProvider = likeProvider;
        this.likeDao = likeDao;
        this.jwtService = jwtService;
    }

    @Transactional(rollbackOn = BaseException.class)
    public PostLikeRes createLike(int employmentIdx, Long userIdx) throws BaseException {
        int status = likeDao.getLikeEmployment(employmentIdx, userIdx);
        System.out.println(status);
        if(status != 0 ){
            throw new BaseException(POST_LIKE_EXISTS);
        }
        try{
            int likeIdx = likeDao.createLike(employmentIdx, userIdx);
            return new PostLikeRes(likeIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
