package com.example.demo.src.apply;

import com.example.demo.config.BaseException;
import com.example.demo.src.apply.model.PostApplyReq;
import com.example.demo.src.company.model.PatchCompanyReq;
import com.example.demo.src.company.model.PostCompanyReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.DUPLICATED_COMPANY_NAME;

@Service
@Transactional(readOnly = false)
public class ApplyService {
    private final ApplyDao applyDao;

    @Autowired
    public ApplyService(ApplyDao applyDao) {
        this.applyDao = applyDao;
    }

    //채용 생성
    public void createApplication(PostApplyReq postApplyReq, Long userIdx, int employmentIdx) throws BaseException{
        try{
            applyDao.createApplication(postApplyReq, userIdx, employmentIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
