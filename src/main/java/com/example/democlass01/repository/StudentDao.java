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
     * 自定义查询：获取某个学生(stu)所选的所有教学班(TClass)
     * * JPQL解析：
     * from Selection s   -> 从 Selection 实体中查询
     * where s.student = :stu -> 筛选出 student 属性等于参数 stu 的记录
     * select s.tclass    -> 返回这些记录中的 tclass 属性
     */
    @Query(value = "select s.tclass from Selection s where s.student = :stu")
    List<TClass> getMyClasses(@Param(value = "stu") Student stu);

    // 也可以使用 DQM (Derived Query Methods) 方式，虽然方法名会比较长，但也能实现：
    // List<Student> findByStudentName(String studentName);
}