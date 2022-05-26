package com.example.demo.src.resume.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class PatchResumeReq {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UpdateResumeTitleReq{

        @Size(max = 100, message = "이력서 제목의 최대 길이는 100글자 입니다.")
        @NotBlank(message = "이력서 제목은 필수로 입력되어야 합나다.")
        private String title;
    }
}
