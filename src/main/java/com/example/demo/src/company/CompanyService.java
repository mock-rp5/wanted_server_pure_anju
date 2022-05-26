package com.example.demo.src.company;


import com.example.demo.config.BaseException;
import com.example.demo.src.company.model.PostCompanyReq;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.DUPLICATED_COMPANY_NAME;


@Service
public class CompanyService {
    private final CompanyProvider companyProvider;
    private final CompanyDao companyDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CompanyService(CompanyProvider companyProvider, CompanyDao companyDao, JwtService jwtService) {
        this.companyProvider = companyProvider;
        this.companyDao = companyDao;
        this.jwtService = jwtService;
    }


    public void createCompany(PostCompanyReq postCompanyReq) throws BaseException {

        if(!companyDao.getCompanyName(postCompanyReq.getCompanyName())){
            throw new BaseException(DUPLICATED_COMPANY_NAME);
        }
        companyDao.createCompany(postCompanyReq);

    }

}
