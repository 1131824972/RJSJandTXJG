package com.example.democlass01.repository;
import com.example.democlass01.entity.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherDao extends LogicDAO<Teacher, Long> {
}