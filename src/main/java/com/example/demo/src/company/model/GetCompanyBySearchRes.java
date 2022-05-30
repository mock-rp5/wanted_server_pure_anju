package com.example.demo.src.company.model;

import com.example.demo.src.employment.model.EmploymentBySearch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCompanyBySearchRes {
    private int countOfCompany;
    private int companyIdx;
    private String logo;
    private String companyName;
    private String industry;
    private int isFollowed;
    private int countOfEmployment;
    private List<EmploymentBySearch> employmentBySearchList;
}
