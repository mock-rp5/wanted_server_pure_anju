package com.example.demo.src.company;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.company.model.*;


import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.GET_SEARCH_FAIL;


@RestController
@RequestMapping("/app")
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
    @GetMapping("/companies/{companyIdx}")
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
    @PostMapping("/companies")
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
    @PatchMapping("/companies/{companyIdx}")
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
    @PatchMapping("/companies/{companyIdx}/unregister")
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

    /**
     * 특정조건으로 회사 조회 API (조건: companyName)
     * [GET]
     */
    @ResponseBody
    @GetMapping("/main/search")
    public BaseResponse<GetCompanyBySearchRes> getCompanyBySearch(@RequestParam(value = "condition", required = false) String condition){
        if(condition.equals("")){
            return new BaseResponse<>(GET_SEARCH_FAIL);
        }

        try {
            Long userIdx = jwtService.getUserIdx();
            GetCompanyBySearchRes getCompanyBySearchRes = companyProvider.getCompanyBySearch(userIdx, condition);
            return new BaseResponse<>(getCompanyBySearchRes);

        }  catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 태그로 회사 조회 API (태그 : #연봉상위, #자기계발..)
     * [GET]
     */
    @ResponseBody
    @GetMapping("/main/tag-search")
    public BaseResponse<List<GetCompanyByTagRes>> getCompanyByTagRes(@RequestParam(value = "tag", required = false) String tag){
        if(tag.equals("")){
            return new BaseResponse<>(GET_SEARCH_FAIL);
        }

        try{
            Long userIdx = jwtService.getUserIdx();
            List<GetCompanyByTagRes> getCompanyByTagRes = companyProvider.getCompanyByTag(userIdx, tag);
            return new BaseResponse<>(getCompanyByTagRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}




