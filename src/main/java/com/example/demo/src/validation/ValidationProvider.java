package com.example.demo.src.validation;

import com.example.demo.config.BaseException;
import com.example.demo.src.validation.model.email.PostEmailReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Slf4j
@Transactional(readOnly = true)
public class ValidationProvider {

    private final ValidationDao validationDao;

    @Autowired
    public ValidationProvider(ValidationDao validationDao) {
        this.validationDao = validationDao;
    }


    public void checkEmail(PostEmailReq postEmailReq) throws BaseException {
        if (validationDao.checkEmail(postEmailReq)) {
            throw new BaseException(DUPLICATED_EMAIL);
        }
    }
}

