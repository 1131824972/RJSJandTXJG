package com.example.democlass01.repository;

import com.example.democlass01.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // 实验一Spring支持：自动注入
public interface StudentRepository extends JpaRepository<Student, Long> {
    // 实验二新增：按用户名查学生（登录/业务查询用）
    Student findByUsername(String username);
}