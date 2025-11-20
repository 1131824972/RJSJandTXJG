package com.example.democlass01.repository;
import com.example.democlass01.entity.Selection;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectionDao extends LogicDAO<Selection, Long> {
}