package com.example.demo.src.apply.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetApplyRes {
    private int total;
    private int applicationCompleted;
    private int documentPass;
    private int finalPass;
    private int fail;
    private List<Company> companyList = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
   public static class Company{
        private String logo;
        private String name;
        private String position;
        private LocalDate writingTime;
        private String status;
        private String recommendStatus;
        private String compensationApplication;
    }

}
