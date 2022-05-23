package com.example.demo.src.profile.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class PutSpecializedFieldReq {

    @NotBlank(message = "유저 식별 값을 입력해주세요.")
    private Long userIdx;

    @NotNull(message = "직군을 선택해 주세요.")
    private String jobGroup;

    @NotNull(message = "직무를 선택해 주세요")
    private String duty;

    @NotNull(message = "경력을 선택해 주세요.")
    private int experience;

    private int presentSalary;
    private String skill;
}
