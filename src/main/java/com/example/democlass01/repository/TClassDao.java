package com.example.democlass01.repository;
import com.example.democlass01.entity.TClass;
import org.springframework.stereotype.Repository;

@Repository
public interface TClassDao extends LogicDAO<TClass, Long> {
}