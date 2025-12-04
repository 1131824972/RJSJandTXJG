package com.example.democlass01.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@DiscriminatorValue("0")  // 单表继承：type=0表示学生
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student extends User {

    @Column(length = 50)  // 适配实验一数据库UTF-8
    private String studentName;  // 学生姓名

    @Column(length = 50)
    private String major;  // 专业（如“计算机科学”）

    @Column(length = 20)
    private String grade;  // 年级（如“2023级”）

    @Column(length = 100)
    private String school;  // 学校（如“XX大学”）

    // 学生-选课：一对多（实验二核心关联）
    @OneToMany(
            mappedBy = "student",  // 由Selection的student维护关联（外键在selection表）
            fetch = FetchType.LAZY,  // 实验一JPA支持延迟加载
            //orphanRemoval = true,  // 移除选课时自动删除Selection（实验二业务需求）
            cascade = CascadeType.ALL  // 级联保存/删除（实验二业务需求）
    )
    @JsonIgnoreProperties(value = {"student"})  // 解决循环引用（实验二新增Jackson配置生效）
    private Set<Selection> selections = new HashSet<>();

    // -------------------------- 实验二业务行为：学生选课 --------------------------
    public Selection selectClass(TClass tClass) {
        // 1. 校验教学班是否满员（实验二业务规则）
        if (tClass.getSelections() != null && tClass.getSelections().size() >= tClass.getCapacity()) {
            return null;  // 满员，选课失败
        }

        // 2. 校验是否已选（避免重复选课）
        for (Selection sel : tClass.getSelections()) {
            if (sel.getStudent().getId().equals(this.getId())) {
                return sel;  // 已选，返回现有记录
            }
        }

        // 3. 新建选课记录（实验一JPA支持级联保存）
        Selection newSel = new Selection();
        newSel.setStudent(this);
        newSel.setTclass(tClass);

        // 4. 维护双向关联（实验二关联一致性要求）
        this.selections.add(newSel);
        //tClass.getSelections().add(newSel);

        return newSel;  // 选课成功
    }

    // -------------------------- 实验二业务行为：学生退课 --------------------------
    public Selection withdraw(TClass tClass) {
        // 1. 找到该学生的选课记录
        Selection toRemove = null;
        for (Selection sel : tClass.getSelections()) {
            if (sel.getStudent().getId().equals(this.getId())) {
                toRemove = sel;
                break;
            }
        }
        if (toRemove == null) return null;  // 未选，退课失败

        // 2. 移除关联（orphanRemoval自动删除数据库记录）
        this.selections.remove(toRemove);
        tClass.getSelections().remove(toRemove);

        return toRemove;  // 退课成功
    }

    // -------------------------- 实验二业务行为：查看课程表 --------------------------
    public List<CourseScheduleDTO> viewCourseSchedule() {
        List<CourseScheduleDTO> schedule = new ArrayList<>();
        for (Selection sel : this.selections) {
            TClass tClass = sel.getTclass();
            Course course = tClass.getCourse();
            Teacher teacher = tClass.getTeacher();

            // 组装课程表DTO
            CourseScheduleDTO dto = new CourseScheduleDTO();
            dto.setCourseName(course.getCourseName());
            dto.setCredit(course.getCredit());
            dto.setTeacherName(teacher.getTeacherName());
            dto.setClassTime(tClass.getTTime());
            dto.setClassWeek(tClass.getTWeek());
            dto.setClassRoom(tClass.getRoom());
            schedule.add(dto);
        }
        return schedule;
    }

    // 课程表DTO
    public static class CourseScheduleDTO {
        private String courseName;
        private String credit;
        private String teacherName;
        private String classTime;
        private String classWeek;
        private String classRoom;

        // Getter & Setter
        public String getCourseName() { return courseName; }
        public void setCourseName(String courseName) { this.courseName = courseName; }
        public String getCredit() { return credit; }
        public void setCredit(String credit) { this.credit = credit; }
        public String getTeacherName() { return teacherName; }
        public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
        public String getClassTime() { return classTime; }
        public void setClassTime(String classTime) { this.classTime = classTime; }
        public String getClassWeek() { return classWeek; }
        public void setClassWeek(String classWeek) { this.classWeek = classWeek; }
        public String getClassRoom() { return classRoom; }
        public void setClassRoom(String classRoom) { this.classRoom = classRoom; }
    }
}