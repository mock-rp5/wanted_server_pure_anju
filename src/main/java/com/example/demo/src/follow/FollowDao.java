package com.example.demo.src.follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FollowDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int getFollowCompany(int companyIdx, Long userIdx){
        String GetFollowCompanyQuery = "select count(companyFollowIdx) from CompanyFollow where userIdx = ? and companyIdx = ? and status = 'ACTIVE'";
        Object[] GetFollowCompanyParams = new Object[]{userIdx, companyIdx};
        return this.jdbcTemplate.queryForObject(GetFollowCompanyQuery, int.class,GetFollowCompanyParams);
    }

    //북마크 생성
    public void createFollow(int companyIdx, Long userIdx){
        String checkStatusSql = "select status from CompanyFollow where userIdx = ? and companyIdx = " + companyIdx + " and status = 'NOTACTIVE'";
        Long checkParam = userIdx;
        List<String> companyFollowList = this.jdbcTemplate.query(checkStatusSql,
                (rs, rowNum) -> rs.getString("status"),
                checkParam);

        if (companyFollowList.isEmpty()){
            String createFollowQuery = "insert into CompanyFollow (companyIdx, userIdx, status) values (?,?,'ACTIVE')";
            Object[] createFollowParams = new Object[]{companyIdx, userIdx};
            this.jdbcTemplate.update(createFollowQuery, createFollowParams);
        } else {
            String updateFollowQuery = "update CompanyFollow set status = 'ACTIVE' where userIdx = ? and companyIdx = ?";
            Object[] updateFollowParams = new Object[]{userIdx, companyIdx};

            this.jdbcTemplate.update(updateFollowQuery, updateFollowParams);
        }

    }
}

