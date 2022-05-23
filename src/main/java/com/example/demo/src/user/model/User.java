package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userIdx;

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(Long userIdx, String email, String password) {
        this.userIdx = userIdx;
        this.email = email;
        this.password = password;
    }
}
