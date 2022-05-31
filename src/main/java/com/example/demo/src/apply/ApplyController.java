package com.example.demo.src.apply;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.apply.model.GetApplyRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
