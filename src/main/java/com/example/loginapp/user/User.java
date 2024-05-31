package com.example.loginapp.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String address; // 상품 구매시 받기

    //오어스를 구분할 필드
    private String provider; // facebook, kakao, apple, naver

    @Builder
    public User(Integer id, String username, String password, String address, String email, String provider) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.address = address;
        this.email = email;
        this.provider = provider;
    }
}
