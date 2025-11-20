package com.example.democlass01.repository;

import com.example.democlass01.entity.Student;
import com.example.democlass01.entity.TClass;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao extends LogicDAO<Student, Long> {

    /**
     * Query 自定义 JPQL 查询
     * 业务：获取该学生选的所有教学班
     */
    @Query(value = "select s.tclass from Selection s where s.student = :stu")
    List<TClass> getMyClasses(@Param(value = "stu") Student stu);

    /**
     * DQM 语义解析查询
     * 业务：根据学生姓名模糊查询
     */
    List<Student> findByStudentNameLike(String studentName);

    // 业务：根据用户名精确查询 (原 StudentRepository 中的功能)
    Student findByUsername(String username);
}