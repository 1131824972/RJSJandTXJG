package com.example.democlass01.service;

import com.example.democlass01.entity.User;
import com.example.democlass01.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service // 标记这是一个Spring服务Bean
public class UserService extends LogicService<User, Long> {

    @Resource
    private UserDao userDao;

    public UserService(UserDao userDao) {
        super(userDao); // 将具体的DAO传给父类
        this.userDao = userDao;
    }

    // 可以在这里扩展登录逻辑，目前先留空或做简单实现
    public String login(String username, String password) {
        // TODO: 后续结合Shiro或Spring Security实现
        return "Login logic placeholder";
    }
}