package com.example.democlass01.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Where(clause = "is_deleted=0")  // 过滤未删除课程（实验一数据库支持SQL条件）
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course extends LogicEntity {

    @Column(length = 100, nullable = false)  // 适配实验一数据库UTF-8，课程名非空
    private String courseName;  // 课程名称（如“Java编程”）

    @Column(nullable = false)
    private Integer creditHours;  // 学时（如48）

    @Column(length = 10, nullable = false)
    private String credit;  // 学分（如“3.0”）

    // 课程-教学班：一对多（实验二核心关联）
    @OneToMany(
            mappedBy = "course",  // 由TClass的course维护关联（外键在t_class表）
            fetch = FetchType.LAZY,  // 实验一JPA支持延迟加载
            cascade = CascadeType.ALL  // 级联保存教学班（实验二业务需求）
    )
    @JsonIgnoreProperties(value = {"course"})  // 解决循环引用
    private Set<TClass> clses = new HashSet<>();
}