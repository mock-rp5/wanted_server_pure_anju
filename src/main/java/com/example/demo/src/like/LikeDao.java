package com.example.demo.src.like;

import com.example.demo.src.like.model.PostLikeReq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Repository
public class LikeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createLike(int employmentIdx, Long userIdx) {
        String createLikeQuery = "insert into EmploymentLike(employmentIdx, userIdx) values (?,?)";
        Object[] createLikeParams = new Object[]{employmentIdx, userIdx};
        this.jdbcTemplate.update(createLikeQuery, createLikeParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int getLikeEmployment(int employmentIdx, Long userIdx) {
        String GetLikeProductQuery = "select count(employmentLikeIdx) from EmploymentLike " +
                "where userIdx = ? and employmentIdx = ?";
        Object[] GetLikeProductParams = new Object[]{userIdx, employmentIdx};
        return this.jdbcTemplate.queryForObject(GetLikeProductQuery, int.class,GetLikeProductParams);
    }
}
