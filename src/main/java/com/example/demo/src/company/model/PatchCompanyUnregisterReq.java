package com.example.demo.src.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchCompanyUnregisterReq {

    @AssertTrue(message = "탈퇴약관을 동의해주세요.")
    private Boolean isAgreed;
}
