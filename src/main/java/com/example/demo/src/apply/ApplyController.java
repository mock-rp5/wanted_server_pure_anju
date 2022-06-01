package com.example.demo.src.apply;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.apply.model.GetApplyRes;
import com.example.demo.src.apply.model.PostApplyReq;
import com.example.demo.src.company.model.PostCompanyReq;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/app/applies")
public class ApplyController {

    private final JwtService jwtService;
    private final ApplyProvider applyProvider;
    private final ApplyService applyService;

    @Autowired
    public ApplyController(JwtService jwtService, ApplyProvider applyProvider, ApplyService applyService) {
        this.jwtService = jwtService;
        this.applyProvider = applyProvider;
        this.applyService = applyService;
    }


    /**
     * 지원 현황 조회 API
     * [GET] /app/applies
     */
    @GetMapping("")
    public BaseResponse<GetApplyRes> retrieveApply(){
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(applyProvider.retrieveApply(userIdx));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 채용중인 회사 포지션 지원 생성 API
     * [POST]
     */
    @ResponseBody
    @PostMapping("/employments/{employmentIdx}")
    public BaseResponse<String> createApplication(@Valid @RequestBody PostApplyReq postApplyReq, @PathVariable("employmentIdx") int employmentIdx){
        try{

            Long userIdx = jwtService.getUserIdx();

            String result = "";
            if (postApplyReq.getStatus().equals("complete")){
                result = "지원완료";
         
            }
            applyService.createApplication(postApplyReq, userIdx, employmentIdx);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
}
