package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResumeReq {

    @NotEmpty(message = "이력서 제목을 입력해주세요")
    private String title;

    @NotEmpty(message = "이름을 입력해 주세요")
    private String name;

    @Email(message = "이메일 형식을 확인해 주세요")
    private String email;

    @NotBlank(message = "휴대번호는 -를 제외한 11자리를 입력해 주세요.")
    @Size(min = 11, max = 11)
    private String phoneNumber;

    private String content;

    @NotNull(message = "작성완료 또는 임시저장 여부를 확인해 주세요")
    private Boolean isCompleted;

    private List<Career> careers;
    private List<Education> educations;
    private List<Skill> skills;
    private List<Awards> awards;
    private List<ForeignLanguage> foreignLanguages;
    private List<Portfolio> portfolios;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Career {
        private String companyName;
        private String employmentForm;
        private String startDate;
        private String resignationDate;
        private Boolean isPresented;
        private List<MajorAccomplishment> majorAccomplishments;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MajorAccomplishment {
        private String content;
        private String startDate;
        private String endDate;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Education {
        private String schoolName;
        private String major;
        private String admissionDate;
        private String graduationDate;
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
        private String awardsDate;
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
        private String acquisitionDate;
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
