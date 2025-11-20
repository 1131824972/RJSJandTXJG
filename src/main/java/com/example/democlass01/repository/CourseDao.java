package com.example.democlass01.repository;

import com.example.democlass01.entity.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDao extends LogicDAO<Course, Long> {
    // 示例 DQM：根据课程名称查询
    Course findByCourseName(String courseName);
}