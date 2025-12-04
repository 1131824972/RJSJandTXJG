package com.example.democlass01.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@DiscriminatorValue("1")  // 单表继承：type=1表示教师（实验二约定）
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Teacher extends User {

    @Column(length = 50)  // 适配实验一数据库UTF-8
    private String teacherName;  // 教师姓名

    @Column(length = 100)
    private String school;  // 学校（如“XX大学”）

    @Column(length = 100)
    private String research;  // 研究方向（如“软件工程”）

    // 教师-教学班：一对多（实验二核心关联）
    @OneToMany(
            mappedBy = "teacher",  // 由TClass的teacher维护关联（外键在t_class表）
            fetch = FetchType.LAZY,  // 实验一JPA支持延迟加载
            cascade = CascadeType.ALL  // 级联保存教学班（实验二业务需求）
    )
    @JsonIgnoreProperties(value = {"teacher"})  // 解决循环引用
    private Set<TClass> clses = new HashSet<>();

    // -------------------------- 实验二业务行为：给学生打期中成绩 --------------------------
    public void rankMidScore(TClass tClass, Student student, Double midScore) {
        // 1. 校验：教学班是否属于当前教师（实验二权限控制）
        if (!this.clses.contains(tClass)) {
            throw new IllegalArgumentException("该教学班不属于您，无法打分");
        }

        // 2. 找到学生的选课记录
        Selection selection = null;
        for (Selection sel : tClass.getSelections()) {
            if (sel.getStudent().getId().equals(student.getId())) {
                selection = sel;
                break;
            }
        }
        if (selection == null) {
            throw new IllegalArgumentException("该学生未选此课，无法打分");
        }

        // 3. 设置期中成绩（实验一JPA支持更新）
        selection.setMidScore(midScore);
    }

    // -------------------------- 实验二业务行为：给学生打平时成绩 --------------------------
    public void rankPerformanceScore(TClass tClass, Student student, Double performanceScore) {
        if (!this.clses.contains(tClass)) {
            throw new IllegalArgumentException("该教学班不属于您，无法打分");
        }
        Selection selection = null;
        for (Selection sel : tClass.getSelections()) {
            if (sel.getStudent().getId().equals(student.getId())) {
                selection = sel;
                break;
            }
        }
        if (selection == null) {
            throw new IllegalArgumentException("该学生未选此课，无法打分");
        }
        selection.setPerformanceScore(performanceScore);
    }
}