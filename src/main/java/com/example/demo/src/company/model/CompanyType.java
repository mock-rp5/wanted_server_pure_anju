package com.example.demo.src.company.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompanyType {
    private int companyIdx;
    private String logo;
    private String imgUrl;
    private String companyName;
    private String industry;
    private int isFollowed;
}
