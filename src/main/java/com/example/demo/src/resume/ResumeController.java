package com.example.demo.src.resume;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.resume.model.GetResumeRes;
import com.example.demo.src.resume.model.PostResumeReq;
import com.example.demo.src.resume.model.PostResumeRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/resume")
public class ResumeController {


    private final JwtService jwtService;
    private final ResumeService resumeService;
    private final ResumeProvider resumeProvider;

    @Autowired
    public ResumeController(JwtService jwtService, ResumeService resumeService, ResumeProvider resumeProvider) {
        this.jwtService = jwtService;
        this.resumeService = resumeService;
        this.resumeProvider = resumeProvider;
    }

    /**
     * 이력서 작성 API
     * [post] /app/resume
     */
    @PostMapping("")
    public BaseResponse<PostResumeRes> createResume(@Valid @RequestBody PostResumeReq postResumeReq) {
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(resumeService.createResume(userIdx, postResumeReq));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저의 모든 이력서 조회 API
     * [get] /app/resume
     */
    @GetMapping("")
    public BaseResponse<List<GetResumeRes.RetrieveAllResume>> retrieveAllResume() {
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(resumeProvider.retrieveAllResume(userIdx));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 상세 이력서 조회 API
     * [get] /app/resume/:resumeId
     */
    @GetMapping("/{resumeIdx}")
    public BaseResponse<GetResumeRes> retrieveResume(@PathVariable Long resumeIdx) {
        try {
            return new BaseResponse<>(resumeProvider.retrieveResume(resumeIdx));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 이력서 삭제 API
     * [patch] /app/resume/:resumeId/delete
     */
    @GetMapping("/{resumeIdx}/delete")
    public BaseResponse<BaseResponseStatus> deleteResume(@PathVariable Long resumeIdx) {
        try {
            resumeService.deleteResume(resumeIdx);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

