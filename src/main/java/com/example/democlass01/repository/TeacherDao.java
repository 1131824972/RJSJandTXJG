package com.example.democlass01.repository;

import com.example.democlass01.entity.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherDao extends LogicDAO<Teacher, Long> {
    // 示例 DQM：根据教师工号(username)查询
    Teacher findByUsername(String username);
}