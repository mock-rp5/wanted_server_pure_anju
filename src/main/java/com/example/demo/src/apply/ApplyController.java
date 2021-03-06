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

import java.util.List;
import com.example.demo.src.apply.model.GetApplyRes;
import com.example.demo.src.apply.model.PatchApplyReq;
import com.example.demo.src.apply.model.PostApplyReq;
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
    public BaseResponse<GetApplyRes> retrieveApply() {
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(applyProvider.retrieveApply(userIdx));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 작성중인 지원현황 조회 API
     * [GET] /app/applies/write
     */
    @GetMapping("/write")
    public BaseResponse<List<GetApplyRes.GetApplyWritingRes>> retrieveApplyWriting() {
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(applyProvider.retrieveApplyWriting(userIdx));
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
    public BaseResponse<String> createApplication(@Valid @RequestBody PostApplyReq postApplyReq, @PathVariable("employmentIdx") int employmentIdx) {
        try {

            Long userIdx = jwtService.getUserIdx();

            String result = "";
            if (postApplyReq.getStatus().equals("complete")) {
                result = "지원완료";

            }
            applyService.createApplication(postApplyReq, userIdx, employmentIdx);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 채용중인 회사 포지션 지원 수정 API
     * [PATCH]
     */
    @ResponseBody
    @PatchMapping("/employments/{employmentIdx}")
    public BaseResponse<String> updateApplication(@RequestBody PatchApplyReq patchApplyReq, @PathVariable("employmentIdx") int employmentIdx) {
        try {

            Long userIdx = jwtService.getUserIdx();

            String result = "";
            if (patchApplyReq.getStatus().equals("pass")) {
                result = "서류통과";
            } else if (patchApplyReq.getStatus().equals("accept")) {
                result = "최종합격";
            } else {
                result = "불합격";
            }

            applyService.updateApplication(patchApplyReq, userIdx, employmentIdx);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
}
