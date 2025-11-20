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
     * 功能：获取某个学生(stu)所选的所有教学班(TClass)
     */
    @Query(value = "select s.tclass from Selection s where s.student = :stu")
    List<TClass> getMyClasses(@Param(value = "stu") Student stu);

    /**
     * Spring Data JPA 会自动解析方法名生成 SQL：select * from student where student_name = ?
     */
    List<Student> findByStudentName(String studentName);

    // 如果你需要用用户名登录，也可以保留这个 DQM 方法
    Student findByUsername(String username);
}