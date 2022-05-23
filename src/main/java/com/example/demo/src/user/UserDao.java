package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class UserDao {

    private JdbcTemplate jdbcTemplate;
    private JwtService jwtService;

    @Autowired
    public void setDataSource(DataSource dataSource, JwtService jwtService) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jwtService = jwtService;
    }

    public Long createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into User (profileImage, name, email, password, phoneNumber, privacyPolicyAgreement, pushNotification) VALUES (?,?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getProfileImage(), postUserReq.getName(), postUserReq.getEmail(), postUserReq.getPassword(), postUserReq.getPhoneNumber(), postUserReq.getPrivacyPolicyAgreement(), postUserReq.getPushNotification()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);
    }

    public GetUserRes getUser(Long userIdx) {
        String getUserQuery = "select * from User where userIdx = ? AND status = true";
        Object[] getUserParams = new Object[]{userIdx};
        List<Map<String, Object>> user = this.jdbcTemplate.queryForList(getUserQuery, getUserParams);
        return GetUserRes.builder()
                .userIdx((Long) user.get(0).get("userIdx"))
                .jwt(jwtService.createJwt((Long) user.get(0).get("userIdx")))
                .profileImage((String) user.get(0).get("profileImage"))
                .name((String) user.get(0).get("name"))
                .email((String) user.get(0).get("email"))
                .phoneNumber((String) user.get(0).get("phoneNumber"))
                .pushNotification((Boolean) user.get(0).get("pushNotification"))
                .build();
    }

    public User getPwd(PostLoginReq postLoginReq) {
        String getPwdQuery = "select userIdx, email, password from User where email = ? AND status = true";
        String getPwdParams = postLoginReq.getEmail();
        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getLong("userIdx"),
                        rs.getString("email"),
                        rs.getString("password")
                ),
                getPwdParams
        );
    }

}
