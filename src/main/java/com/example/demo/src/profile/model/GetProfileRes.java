package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProfileRes {
    private User user;
    private Resume resume;
    private SpecializedField  specializedField;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class User{
        private Long userIdx;
        private String profileImage;
        private String name;
        private String email;
        private String phoneNumber;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Resume{
        private Long resumeIdx;
        private String resumeTitle;
        private Education education;
        private Career career;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Education{
        private String schoolName;
        private String major;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Career{
        private String companyName;
        private String employmentForm;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SpecializedField{
        private Long specializedFieldIdx;
        private String jobGroup;
        private String duty;
        private Integer experience;
        private String skill;;
    }
}
