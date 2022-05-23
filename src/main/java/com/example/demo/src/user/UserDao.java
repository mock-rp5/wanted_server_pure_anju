package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into User (profileImage, name, email, password, phoneNumber, privacyPolicyAgreement, pushNotification) VALUES (?,?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getProfileImage(), postUserReq.getName(), postUserReq.getEmail(), postUserReq.getPassword(), postUserReq.getPhoneNumber(), postUserReq.getPrivacyPolicyAgreement(), postUserReq.getPushNotification()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);
    }



}
