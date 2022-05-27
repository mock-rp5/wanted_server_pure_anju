package com.example.demo.src.like;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class LikeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // 좋아요 생성
    public void createLike(int employmentIdx, Long userIdx) {
        String checkStatusSql = "select status from EmploymentLike where userIdx = ? and employmentIdx = " + employmentIdx + " and status = 'NOTACTIVE'";
        Long checkParam = userIdx;
        List<String> employmentLikeList = this.jdbcTemplate.query(checkStatusSql,
                (rs, rowNum) -> rs.getString("status"),
                checkParam);

        if (employmentLikeList.isEmpty()) {
            String createLikeQuery = "insert into EmploymentLike (employmentIdx, userIdx, status) values (?,?,'ACTIVE')";
            Object[] createLikeParams = new Object[]{employmentIdx, userIdx};
            this.jdbcTemplate.update(createLikeQuery, createLikeParams);

        } else {
            String updateLikeQuery = "update EmploymentLike set status = 'ACTIVE' where userIdx = ? and employmentIdx = ?";
            Object[] updateLikeParams = new Object[]{userIdx, employmentIdx};

            this.jdbcTemplate.update(updateLikeQuery, updateLikeParams);
        }

    }

    public int getLikeEmployment(int employmentIdx, Long userIdx) {
        String GetLikeProductQuery = "select count(employmentLikeIdx) from EmploymentLike " +
                "where userIdx = ? and employmentIdx = ? and status = 'ACTIVE'";
        Object[] GetLikeProductParams = new Object[]{userIdx, employmentIdx};
        return this.jdbcTemplate.queryForObject(GetLikeProductQuery, int.class,GetLikeProductParams);
    }

    public int cancelLike(int employmentIdx, Long userIdx) {
        String cancelLikeQuery = "update EmploymentLike set status = 'NOTACTIVE'" +
                "where userIdx= ? and employmentIdx = ? ";
        Object[] cancelLikeParams = new Object[]{userIdx, employmentIdx};

        return this.jdbcTemplate.update(cancelLikeQuery,cancelLikeParams);
    }


}
