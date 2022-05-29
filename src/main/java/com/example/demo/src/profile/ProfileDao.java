package com.example.demo.src.profile;

import com.example.demo.src.profile.model.GetProfileRes;
import com.example.demo.src.profile.model.PutSpecializedFieldReq;
import com.example.demo.src.resume.model.GetResumeRes;
import com.example.demo.src.user.model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

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

    // 프로필 조회
    public GetProfileRes retrieveProfile(Long userIdx) {
        String retrieveProfileQuery = "SELECT *\n" +
                "FROM User U\n" +
                "         join Resume R on U.userIdx = R.userIdx\n" +
                "         join Education E on R.resumeIdx = E.resumeIdx\n" +
                "         join Career C on R.resumeIdx = C.resumeIdx\n" +
                "         join SpecializedField SF on U.userIdx = SF.userIdx\n" +
                "WHERE R.isDefaulted = true\n" +
                "  AND U.userIdx = ?";
        Object[] retrieveUserParams = new Object[]{userIdx};
        List<Map<String, Object>> retrieveProfileList = this.jdbcTemplate.queryForList(retrieveProfileQuery, retrieveUserParams);

        return GetProfileRes.builder()
                .user(
                        GetProfileRes.User.builder()
                                .userIdx((Long) retrieveProfileList.get(0).get("userIdx"))
                                .name((String) retrieveProfileList.get(0).get("name"))
                                .profileImage((String) retrieveProfileList.get(0).get("profileImage"))
                                .email((String) retrieveProfileList.get(0).get("email"))
                                .phoneNumber((String) retrieveProfileList.get(0).get("phoneNumber"))
                                .build()
                )
                .resume(
                        GetProfileRes.Resume.builder()
                                .resumeIdx((Long) retrieveProfileList.get(0).get("resumeIdx"))
                                .resumeTitle((String) retrieveProfileList.get(0).get("title"))
                                .education(
                                        GetProfileRes.Education.builder()
                                                .schoolName((String) retrieveProfileList.get(0).get("schoolName"))
                                                .major((String) retrieveProfileList.get(0).get("major"))
                                                .build()
                                )
                                .career(GetProfileRes.Career.builder()
                                        .companyName((String) retrieveProfileList.get(0).get("companyName"))
                                        .employmentForm((String) retrieveProfileList.get(0).get("employmentForm"))
                                        .build()
                                ).build()
                )
                .specializedField(
                        GetProfileRes.SpecializedField.builder()
                                .specializedFieldIdx((Long) retrieveProfileList.get(0).get("specializedFieldIdx"))
                                .jobGroup((String) retrieveProfileList.get(0).get("jobGroup"))
                                .duty((String) retrieveProfileList.get(0).get("duty"))
                                .experience((Integer) retrieveProfileList.get(0).get("experience"))
                                .skill((String) retrieveProfileList.get(0).get("skill"))
                                .build()
                )
                .build();
    }

}
