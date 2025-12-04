package com.example.democlass01.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Where(clause = "is_deleted=0")  // 过滤未删除选课记录
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Selection extends LogicEntity {

    private Double midScore;  // 期中成绩（可null）
    private Double performanceScore;  // 平时成绩（可null）

    // 选课-学生：多对一（实验二核心关联）
    @ManyToOne(fetch = FetchType.LAZY)  // 实验一JPA支持延迟加载
    @JsonIgnoreProperties(value = {"selections"})  // 解决循环引用
    private Student student;

    // 选课-教学班：多对一（实验二核心关联）
    @ManyToOne(fetch = FetchType.LAZY)  // 实验一JPA支持延迟加载
    @JsonIgnoreProperties(value = {"selections"})  // 解决循环引用
    private TClass tclass;
}