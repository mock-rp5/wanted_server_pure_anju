package com.example.demo.src.resume;

import com.example.demo.config.BaseException;
import com.example.demo.src.resume.model.GetResumeRes;
import com.example.demo.src.user.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
public class ResumeProvider {

    private final ResumeDao resumeDao;

    @Autowired
    public ResumeProvider(ResumeDao resumeDao) {
        this.resumeDao = resumeDao;
    }

    // 모든 이력서 조회
    public List<GetResumeRes.RetrieveAllResume> retrieveAllResume(Long userIdx) throws BaseException {
        try {
            return resumeDao.retrieveAllResume(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 이력서 조회
    public GetResumeRes retrieveResume(Long resumeIdx) throws BaseException {
        try {
            return resumeDao.retrieveResume(resumeIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
