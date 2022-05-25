package com.example.demo.src.employment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetEmploymentDetailRes {

    private int employmentIdx;
    private String imgUrl;
    private String title;
    private String companyName;
    private String responseRate;
    private String city;
    private String country;
    private int recommenderReward;
    private int applicantReward;
    private int isBookmarked;
    private int isApplied;
    private int countLike;
    private String introduction;
    private String primaryTask;
    private String qualification;
    private String preferentialTreatment;
    private String welfare;
    private String deadLine;
    private String workArea;
    private List<CompanyTag> companyTagList;
    private List<EmploymentSkill> employmentSkillList;
}
