package com.example.demo.src.company.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCompanyByTagRes {
    private int companyIdx;
    private String logo;
    private String companyName;
    private int isFollowed;
    private String companyTagList;
}
