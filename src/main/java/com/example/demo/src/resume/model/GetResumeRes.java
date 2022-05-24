package com.example.demo.src.resume.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
public class GetResumeRes {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RetrieveAllResume{
        private String title;
        private LocalDate updatedAt;
        private Boolean isDefaulted;
        private Boolean isCompleted;
    }


}
