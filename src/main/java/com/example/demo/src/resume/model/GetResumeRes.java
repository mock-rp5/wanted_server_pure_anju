package com.example.demo.src.resume.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;


@Data
@Builder
public class GetResumeRes {

    private Long resumeIdx;
    private String title;
    private String name;
    private String email;
    private String phoneNumber;
    private String content;
    private Boolean isCompleted;

    private List<GetResumeRes.Career> careers;
    private List<GetResumeRes.Education> educations;
    private List<GetResumeRes.Skill> skills;
    private List<GetResumeRes.Awards> awards;
    private List<GetResumeRes.ForeignLanguage> foreignLanguages;
    private List<GetResumeRes.Portfolio> portfolios;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RetrieveAllResume{
        private Long resumeIdx;
        private String title;
        private LocalDate updatedAt;
        private Boolean isDefaulted;
        private Boolean isCompleted;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Career {
        private String companyName;
        private String employmentForm;
        private LocalDate startDate;
        private LocalDate resignationDate;
        private Boolean isPresented;
        private List<PostResumeReq.MajorAccomplishment> majorAccomplishments;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MajorAccomplishment {
        private String content;
        private LocalDate startDate;
        private LocalDate endDate;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Education {
        private String schoolName;
        private String major;
        private LocalDate admissionDate;
        private LocalDate graduationDate;
        private String content;
        private Boolean isAttended;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Skill {
        private String skillName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Awards {
        private String awardsTitle;
        private String awardsContent;
        private LocalDate awardsDate;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ForeignLanguage {
        private String languageType;
        private String languageLevel;
        private String licenseName;
        private Double licenseScore;
        private LocalDate acquisitionDate;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Portfolio {
        private String portfolioUrl1;
        private String portfolioUrl2;
        private String portfolioUrl3;
    }
}
