package com.example.demo.src.company;


import com.example.demo.config.BaseException;
import com.example.demo.src.company.model.GetCompanyBySearchRes;
import com.example.demo.src.company.model.GetCompanyDetailsRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class CompanyProvider {

    private final CompanyDao companyDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CompanyProvider(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    //회사 상세 조회
    public GetCompanyDetailsRes getCompanyDetails(Long userIdx, int companyIdx) throws BaseException {
        try {
            GetCompanyDetailsRes getEmploymentDetailsRes = companyDao.getCompanyDetails(userIdx, companyIdx);
            return getEmploymentDetailsRes;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetCompanyBySearchRes getCompanyBySearch(Long userIdx, String condition) throws BaseException{
        try{
            GetCompanyBySearchRes getCompanyBySearchRes = companyDao.getCompanyBySearch(userIdx, condition);
            return getCompanyBySearchRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
