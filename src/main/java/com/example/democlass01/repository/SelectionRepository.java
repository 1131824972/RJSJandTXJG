package com.example.democlass01.repository;

import com.example.democlass01.entity.Selection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectionRepository extends JpaRepository<Selection, Long> {
    // 实验二新增：按学生ID+教学班ID查选课记录（判断是否已选课）
    Selection findByStudentIdAndTclassId(Long studentId, Long tclassId);
}