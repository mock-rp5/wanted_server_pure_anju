package com.example.demo.src.employment.model;

import com.example.demo.src.company.model.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetEmploymentFeedRes {
    private List<EmploymentBySearch> employmentBySearchList;
    private List<CompanyType> companyTypeList1;
    private List<CompanyType> companyTypeList2;
}
