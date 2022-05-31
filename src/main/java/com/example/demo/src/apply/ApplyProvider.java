package com.example.demo.src.apply;

import com.example.demo.config.BaseException;
import com.example.demo.src.apply.model.GetApplyRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ApplyProvider {
    private final ApplyDao applyDao;

    @Autowired
    public ApplyProvider(ApplyDao applyDao) {
        this.applyDao = applyDao;
    }

    public GetApplyRes retrieveApply(Long userIdx) throws BaseException {
        return applyDao.retrieveApply(userIdx);
    }
}
