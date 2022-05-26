package com.example.demo.src.company.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompanyPosition {

    private int employmentIdx;
    private String title;
    private int totalReward;
    private String deadLine;
    private int isBookmarked;

}
