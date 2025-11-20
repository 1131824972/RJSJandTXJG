package com.example.democlass01.repository;

import com.example.democlass01.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // 实验二新增：按用户名查教师
    Teacher findByUsername(String username);
}