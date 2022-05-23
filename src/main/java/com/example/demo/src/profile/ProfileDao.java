package com.example.demo.src.profile;

import com.example.demo.src.profile.model.PutSpecializedFieldReq;
import com.example.demo.src.user.model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ProfileDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // 전분 분야 업데이트
    public void modifyProfileSpecializedField(PutSpecializedFieldReq putSpecializedFieldReq) {
        String modifyProfileSpecializedField = "INSERT INTO SpecializedField (userIdx, jobGroup, duty, experience, presentSalary, skill) values (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE jobGroup = ?, duty = ?, experience = ?, presentSalary = ?, skill =?";
        Object[] modifyProfileSpecializedFieldParams = new Object[]{putSpecializedFieldReq.getUserIdx(), putSpecializedFieldReq.getJobGroup(), putSpecializedFieldReq.getDuty(), putSpecializedFieldReq.getExperience(), putSpecializedFieldReq.getPresentSalary(), putSpecializedFieldReq.getSkill(),
               putSpecializedFieldReq.getJobGroup(), putSpecializedFieldReq.getDuty(), putSpecializedFieldReq.getExperience(), putSpecializedFieldReq.getPresentSalary(), putSpecializedFieldReq.getSkill()
        };
        this.jdbcTemplate.update(modifyProfileSpecializedField, modifyProfileSpecializedFieldParams);
    }

}
