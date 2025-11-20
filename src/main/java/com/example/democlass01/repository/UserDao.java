package com.example.democlass01.repository;

import com.example.democlass01.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends LogicDAO<User, Long> {
    // 用于登录验证
    User findByUsernameAndPassword(String username, String password);
}