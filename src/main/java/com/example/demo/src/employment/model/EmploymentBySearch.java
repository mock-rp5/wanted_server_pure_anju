package com.example.demo.src.employment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmploymentBySearch {
        private int employmentIdx;
        private String imgUrl;
        private int isBookmark;
        private String title;
        private String companyName;
        private String city;
        private String country;
        private int totalReward;
}
