package com.example.democlass01.repository;

import com.example.democlass01.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // 实验二新增：按课程名模糊查询（如“Java”查所有Java相关课程）
    List<Course> findByCourseNameContaining(String courseName);
}