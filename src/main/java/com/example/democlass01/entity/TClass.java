package com.example.democlass01.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Where(clause = "is_deleted=0")  // 过滤未删除教学班（实验一数据库支持）
public class TClass extends LogicEntity {

    @Column(length = 50)  // 适配实验一数据库UTF-8
    private String TTime;  // 上课时间（如“周一3-4节”）

    @Column(length = 20)
    private String TWeek;  // 上课周次（如“1-16周”）

    @Column(length = 50)
    private String room;  // 上课教室（如“教1-101”）

    @Column(nullable = false)
    private Integer capacity;  // 教学班容量（如50人）

    // 教学班-课程：多对一（实验二核心关联）
    @ManyToOne(fetch = FetchType.LAZY)  // 实验一JPA支持延迟加载
    @JsonIgnoreProperties(value = {"clses"})  // 解决循环引用
    private Course course;

    // 教学班-教师：多对一（实验二核心关联）
    @ManyToOne(fetch = FetchType.LAZY)  // 实验一JPA支持延迟加载
    @JsonIgnoreProperties(value = {"clses"})  // 解决循环引用
    private Teacher teacher;

    // 教学班-选课：一对多（实验二核心关联）
    @OneToMany(
            mappedBy = "tclass",  // 由Selection的tclass维护关联（外键在selection表）
            fetch = FetchType.LAZY,  // 实验一JPA支持延迟加载
            orphanRemoval = true,  // 移除选课时自动删除Selection（实验二业务需求）
            cascade = CascadeType.ALL  // 级联保存选课记录（实验二业务需求）
    )
    @JsonIgnoreProperties(value = {"tclass"})  // 解决循环引用
    private Set<Selection> selections = new HashSet<>();

    // -------------------------- 实验二业务行为：分配教师 --------------------------
    public Teacher assignTeacher(Teacher teacher) {
        this.setTeacher(teacher);
        teacher.getClses().add(this);  // 维护双向关联（实验二关联一致性）
        return teacher;
    }

    // -------------------------- 实验二业务行为：分配课程 --------------------------
    public Course assignCourse(Course course) {
        this.setCourse(course);
        course.getClses().add(this);  // 维护双向关联
        return course;
    }

    // -------------------------- 实验二业务行为：查看班级花名册 --------------------------
    public List<Student> listStudents() {
        List<Student> students = new ArrayList<>();
        for (Selection sel : this.selections) {
            students.add(sel.getStudent());  // 从选课记录中提取学生
        }
        return students;
    }
}