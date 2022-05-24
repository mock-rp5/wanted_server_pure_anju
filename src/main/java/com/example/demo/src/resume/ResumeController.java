package com.example.demo.src.resume;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.resume.model.PostResumeReq;
import com.example.demo.src.resume.model.PostResumeRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/resume")
public class ResumeController {


    private final JwtService jwtService;
    private final ResumeService resumeService;

    @Autowired
    public ResumeController(JwtService jwtService, ResumeService resumeService) {
        this.jwtService = jwtService;
        this.resumeService = resumeService;
    }

    @PostMapping("")
    public BaseResponse<PostResumeRes> postResume(@Valid @RequestBody PostResumeReq postResumeReq) {
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(resumeService.createResume(userIdx, postResumeReq));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
