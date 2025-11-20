package com.example.democlass01.repository;
import com.example.democlass01.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends LogicDAO<User, Long> {
    // 可以添加根据用户名查找用户的方法，用于登录
    User findByUsername(String username);
}