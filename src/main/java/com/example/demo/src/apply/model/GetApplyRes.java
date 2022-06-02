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
    private Long total = 0L;
    private Long applicationCompleted = 0L;
    private Long documentPass = 0L;
    private Long finalPass = 0L;
    private Long fail = 0L;
    private List<Company> companyList = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Company {
        private String logo;
        private String name;
        private String position;
        private LocalDate writingTime;
        private String status;
        private String recommendStatus;
        private String compensationApplication;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetApplyWritingRes {
        private String logo;
        private String name;
        private String position;
        private LocalDate writingTime;
        private String status;
        private String recommendStatus;
    }

}
