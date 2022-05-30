package com.example.demo.src.company.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCompanyDetailsRes {
    private int companyIdx;
    private String logo;
    private String companyName;
    private int isFollowed;
    private String responseRate;
    private String introduction;
    private String homePageUrl;
    private int newAverageSalary;
    private int totalAverageSalay;
    private int employeeNumber;
    private List<CompanyPosition> companyPositions;
    private List<CompanyImage> companyImages;
    private List<CompanyTag> companyTags;
    private List<CompanyNews> companyNews;
}
