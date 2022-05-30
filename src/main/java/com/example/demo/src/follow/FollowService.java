package com.example.demo.src.follow;

import com.example.demo.config.BaseException;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class FollowService {
    private final FollowProvider followProvider;
    private final FollowDao followDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FollowService(FollowProvider followProvider, FollowDao followDao, JwtService jwtService) {
        this.followProvider = followProvider;
        this.followDao = followDao;
        this.jwtService = jwtService;
    }

    public void createFollow(int companyIdx, Long userIdx) throws BaseException {
        int status = followDao.getFollowCompany(companyIdx, userIdx);
        System.out.println(status);
        if(status != 0 ){
            throw new BaseException(POST_FOLLOW_EXISTS);
        }
        try{
            followDao.createFollow(companyIdx, userIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void cancelFollow(int companyIdx, Long userIdx) throws BaseException {
        try{
            int result = followDao.cancelFollow(companyIdx, userIdx);

            if(result == 0){
                throw new BaseException(DELETION_FAIL_FOLLOW);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
