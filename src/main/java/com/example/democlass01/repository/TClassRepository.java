package com.example.democlass01.repository;

import com.example.democlass01.entity.TClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TClassRepository extends JpaRepository<TClass, Long> {
    // 实验二新增：按课程ID查教学班（如查“Java编程”的所有教学班）
    List<TClass> findByCourseId(Long courseId);
}