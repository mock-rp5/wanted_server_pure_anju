package com.example.demo.src.employment;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.employment.model.GetEmploymentDetailRes;
import com.example.demo.src.employment.model.GetEmploymentFeedRes;
import com.example.demo.src.employment.model.GetEmploymentRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/app")
public class EmploymentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final EmploymentProvider employmentProvider;
    @Autowired
    private final EmploymentService employmentService;
    @Autowired
    private final JwtService jwtService;

    public EmploymentController(EmploymentProvider employmentProvider, EmploymentService employmentService, JwtService jwtService) {
        this.employmentProvider = employmentProvider;
        this.employmentService = employmentService;
        this.jwtService = jwtService;
    }

    /**
     * 채용 공고 화면 조회 API
     * [GET}
     */
    @ResponseBody
    @GetMapping("/employmentList")
    public BaseResponse<GetEmploymentRes> getEmployments(@RequestParam("country") String country, @RequestParam("sort") String sort,
                                                         @RequestParam("years1") int years1, @RequestParam("years2") int years2) {

        try{
            Long userIdx = jwtService.getUserIdx();
            GetEmploymentRes getEmploymentRes = employmentProvider.getEmployments(userIdx, country, sort, years1, years2);
            return new BaseResponse<>(getEmploymentRes);


        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 채용공고 상세 화면 조회 API
     * [GET}
     */
    @ResponseBody
    @GetMapping("/employments/{employmentIdx}")
    public BaseResponse<GetEmploymentDetailRes> getEmploymentDetails(@PathVariable int employmentIdx){
        try{
            Long userIdx = jwtService.getUserIdx();
            GetEmploymentDetailRes getEmploymentDetailRes = employmentProvider.getEmploymentDetails(userIdx, employmentIdx);
            return new BaseResponse<>(getEmploymentDetailRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 채용정보 조회 API
     * [GET]
     */
    @ResponseBody
    @GetMapping("/jobsfeed")
    public BaseResponse<GetEmploymentFeedRes> getEmploymentFeed(){
        try{
            Long userIdx = jwtService.getUserIdx();
            GetEmploymentFeedRes getEmploymentFeedRes = employmentProvider.getEmploymentFeed(userIdx);
            return new BaseResponse<>(getEmploymentFeedRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
