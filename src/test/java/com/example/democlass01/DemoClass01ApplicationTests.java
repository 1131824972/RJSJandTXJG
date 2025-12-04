package com.example.democlass01;

import com.example.democlass01.entity.*;
import com.example.democlass01.repository.*;
import com.example.democlass01.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class DemoClass01ApplicationTests {

    // 注入我们写好的 Service
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TClassService tClassService;

    // 注入 DAO 用于辅助验证数据
    @Autowired
    private SelectionDao selectionDao;

    @Test
    @Transactional // 加上这个注解，测试结束后会自动回滚数据，保持数据库干净。如果你想去数据库里看结果，可以把这行注释掉。
    //@Rollback(false)// 让测试数据提交到数据库，不回滚
    void testBusinessLogic() {
        System.out.println("========== 开始测试业务逻辑 ==========");

        // 1. 准备基础数据 (模拟教务处排课)
        // 创建课程
        Course course = new Course();
        course.setCourseName("软件体系结构");
        course.setCredit("4.0");
        course.setCreditHours(64);
        course = courseService.save(course);

        // 创建教师
        Teacher teacher = new Teacher();
        teacher.setTeacherName("谢诚老师");
        teacher.setUsername("xiecheng");
        teacher = teacherService.save(teacher);

        // 创建教学班
        TClass tClass = new TClass();
        tClass.setRoom("实训楼101");
        tClass.setCapacity(60);
        tClass.setTTime("周三 1-2节");
        tClass = tClassService.save(tClass);

        // 利用 TClassService 分配课程和教师
        tClassService.assignCourse(tClass.getId(), course.getId());
        tClassService.assignTeacher(tClass.getId(), teacher.getId());
        System.out.println("✅ 教学班初始化完成：[课程]" + course.getCourseName() + " [教师]" + teacher.getTeacherName());

        // 2. 准备学生数据
        Student student = new Student();
        student.setStudentName("张三");
        student.setSchool("软件学院");
        student = studentService.save(student);
        System.out.println("✅ 学生初始化完成：" + student.getStudentName());

        // 3. 测试【学生选课】功能
        System.out.println("👉 正在执行选课...");
        Selection selection = studentService.selectClass(student.getId(), tClass.getId());

        if (selection != null) {
            System.out.println("✅ 选课成功！生成的选课记录ID：" + selection.getId());
        } else {
            System.err.println("❌ 选课失败！");
            return;
        }

        // 验证：查询该学生的课程表
        List<TClass> myClasses = studentService.getMyClasses(student.getId());
        System.out.println("📋 " + student.getStudentName() + " 的课程表：");
        for (TClass cls : myClasses) {
            System.out.println("   - " + cls.getCourse().getCourseName() + " (" + cls.getRoom() + ")");
        }

        // 4. 测试【教师打分】功能
        System.out.println("👉 老师正在录入期中成绩...");
        Selection scoredSelection = teacherService.rankMidScore(teacher.getId(), tClass.getId(), student.getId(), 95.0);

        if (scoredSelection != null && scoredSelection.getMidScore() == 95.0) {
            System.out.println("✅ 打分成功！当前分数为：" + scoredSelection.getMidScore());
        } else {
            System.err.println("❌ 打分失败！");
        }

        // 5. 测试【学生退课】功能
        System.out.println("👉 学生正在退课...");
        studentService.withdraw(student.getId(), tClass.getId());

        // 验证：再次查询课程表，应该为空
        List<TClass> myClassesAfterWithdraw = studentService.getMyClasses(student.getId());
        if (myClassesAfterWithdraw.isEmpty()) {
            System.out.println("✅ 退课成功！课程表已清空。");
        } else {
            System.err.println("❌ 退课失败，课程表仍有数据。");
        }

        System.out.println("========== 测试结束 ==========");
    }
}