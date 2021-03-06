package com.example.demo.src.employment;

import com.example.demo.config.BaseException;
import com.example.demo.src.employment.model.GetEmploymentDetailRes;
import com.example.demo.src.employment.model.GetEmploymentFeedRes;
import com.example.demo.src.employment.model.GetEmploymentRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class EmploymentProvider {

    private final EmploymentDao employmentDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public EmploymentProvider(EmploymentDao employmentDao) {
        this.employmentDao = employmentDao;
    }

    //채용공고 조회
    public GetEmploymentRes getEmployments(Long userIdx, String country, String sort, Long years1, Long years2) throws BaseException {
        try {
            GetEmploymentRes getEmploymentRes = employmentDao.getEmployments(userIdx, country, sort, years1, years2);
            return getEmploymentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //채용공고 상세 조회
    public GetEmploymentDetailRes getEmploymentDetails(Long userIdx, int employmentIdx) throws BaseException {
        try {
            GetEmploymentDetailRes getEmploymentDetailRes = employmentDao.getEmployDetails(userIdx, employmentIdx);
            return getEmploymentDetailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //채용정보 조회
    public GetEmploymentFeedRes getEmploymentFeed(Long userIdx) throws BaseException{
        try{
            GetEmploymentFeedRes getEmploymentFeedRes = employmentDao.getEmploymentFeed(userIdx);
            return getEmploymentFeedRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
