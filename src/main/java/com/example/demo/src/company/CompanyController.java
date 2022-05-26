package com.example.demo.src.company;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.company.model.GetCompanyDetailsRes;


import com.example.demo.src.company.model.PatchCompanyReq;
import com.example.demo.src.company.model.PatchCompanyUnregisterReq;
import com.example.demo.src.company.model.PostCompanyReq;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


@RestController
@RequestMapping("/app/companies")
public class CompanyController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CompanyProvider companyProvider;
    @Autowired
    private final CompanyService companyService;
    @Autowired
    private final JwtService jwtService;

    public CompanyController(CompanyProvider companyProvider, CompanyService companyService, JwtService jwtService) {
        this.companyProvider = companyProvider;
        this.companyService = companyService;
        this.jwtService = jwtService;
    }

    /**
     * 회사 상세 조회 API
     * [GET]
     */
    @ResponseBody
    @GetMapping("/{companyIdx}")
    public BaseResponse<GetCompanyDetailsRes> getCompanyDetails(@PathVariable("companyIdx") int companyIdx){
        try {
            Long userIdx = jwtService.getUserIdx();
            GetCompanyDetailsRes getCompanyDetailsRes = companyProvider.getCompanyDetails(userIdx, companyIdx);
            return new BaseResponse<>(getCompanyDetailsRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 회사 정보 생성 API
     * [POST]
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> createCompany(@Valid @RequestBody PostCompanyReq postCompanyReq)  {

        try{
            companyService.createCompany(postCompanyReq);
            String result = "";
            return new BaseResponse<>(result);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회사 정보 수정 API
     * [PATCH]
     */
    @ResponseBody
    @PatchMapping("/{companyIdx}")
    public BaseResponse<String> modifyCompany(@Valid @RequestBody PatchCompanyReq patchCompanyReq,
                                              @PathVariable int companyIdx) {
        try{
            companyService.modifyCompany(patchCompanyReq, companyIdx);
            String result = "회사 정보가 수정되었습니다.";
            return new BaseResponse<>(result);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }


    }

    /**
     * 회사 정보 삭제 API
     * [PATCH]
     */
    @ResponseBody
    @PatchMapping("/{companyIdx}/unregister")
    public BaseResponse<String> deleteCompany(@Valid @RequestBody PatchCompanyUnregisterReq patchCompanyUnregisterReq,
                                              @PathVariable int companyIdx) {
        try{
            companyService.deleteCompany(companyIdx);
            String result = "회사 정보가 삭제되었습니다.";
            return new BaseResponse<>(result);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }


    }



}
