package com.example.demo.src.employment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Company {
    private int companyIdx;
    private String logo;
    private String imgUrl;
    private String companyName;
    private int countPosition;
}
