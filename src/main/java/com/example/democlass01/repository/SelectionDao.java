package com.example.democlass01.repository;

import com.example.democlass01.entity.Selection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectionDao extends LogicDAO<Selection, Long> {
    @Query("select s from Selection s where s.student.id = ?1 and s.tclass.id = ?2")
    Selection findByStuIdAndClsId(Long stuId, Long clsId);
}