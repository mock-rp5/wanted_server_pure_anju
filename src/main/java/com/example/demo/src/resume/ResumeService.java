package com.example.demo.src.resume;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.profile.model.PutSpecializedFieldReq;
import com.example.demo.src.resume.model.PatchResumeReq;
import com.example.demo.src.resume.model.PostResumeReq;
import com.example.demo.src.resume.model.PostResumeRes;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.UserDto;
import com.example.demo.utils.SHA256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional(readOnly = false)
@Slf4j
public class ResumeService {

    private final ResumeDao resumeDao;

    @Autowired
    public ResumeService(ResumeDao resumeDao) {
        this.resumeDao = resumeDao;
    }

    // 이력서 생성
    public PostResumeRes createResume(Long userIdx, PostResumeReq postResumeReq) throws BaseException {
        try {
            return resumeDao.createResume(userIdx, postResumeReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    // 이력서 삭제
    public void deleteResume(Long resumeIdx) throws BaseException {
        try {
            resumeDao.deleteResume(resumeIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    // 이력서 이름 변경
    public void updateResumeTitle(Long resumeIdx, PatchResumeReq.UpdateResumeTitleReq updateResumeTitleReq) throws BaseException {
        try {
            resumeDao.updateResumeTitle(resumeIdx, updateResumeTitleReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 기본 이력서로 변경
    public void updateResumeDefault(Long resumeIdx) throws BaseException {
        try {
            resumeDao.updateResumeDefault(resumeIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 이력서 작성상태 변경
    public void updateResumeCompleted(Long resumeIdx) throws BaseException {
        try {
            resumeDao.updateResumeCompleted(resumeIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 이력서 수정
    public void updateResume(Long resumeIdx, PatchResumeReq patchResumeReq) throws BaseException {
        try {
            resumeDao.updateResume(resumeIdx, patchResumeReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
