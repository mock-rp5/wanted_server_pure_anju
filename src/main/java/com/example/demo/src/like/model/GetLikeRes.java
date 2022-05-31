package com.example.demo.src.like.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetLikeRes {

    private int employmentIdx;
    private String imgUrl;
    private int countEmploymentLike;
    private String title;
    private String companyName;
    private String city;
    private String country;
    private int totalReward;

}
