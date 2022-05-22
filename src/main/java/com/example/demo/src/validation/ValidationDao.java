package com.example.demo.src.validation;

import com.example.demo.src.validation.model.email.PostEmailReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ValidationDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Boolean checkEmail(PostEmailReq postEmailReq) {
        String checkEmailQuery = "select exists(select User.email from User where email = ? AND User.status = true)";
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                Boolean.class,
                postEmailReq.getEmail());
    }
}
