package com.example.democlass01.service;

import com.example.democlass01.entity.Course;
import com.example.democlass01.entity.Student;
import com.example.democlass01.entity.TClass;
import com.example.democlass01.entity.Teacher;
import com.example.democlass01.repository.CourseDao;
import com.example.democlass01.repository.TClassDao;
import com.example.democlass01.repository.TeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TClassService extends LogicService<TClass, Long> {

    private final TClassDao tClassDao;

    @Resource
    private TeacherDao teacherDao;

    @Resource
    private CourseDao courseDao;

    public TClassService(@Autowired TClassDao dao) {
        super(dao);
        this.tClassDao = dao;
    }

    /**
     * 为教学班分配教师
     */
    @Transactional
    public Teacher assignTeacher(Long clsId, Long teacherId) {
        TClass tClass = tClassDao.getReferenceById(clsId);
        Teacher teacher = teacherDao.getReferenceById(teacherId);

        // 调用模型层业务逻辑（实验2写的代码）
        tClass.assignTeacher(teacher);

        // 保存更新
        tClassDao.save(tClass);
        return teacher;
    }

    /**
     * 为教学班指定课程
     */
    @Transactional
    public Course assignCourse(Long clsId, Long courseId) {
        TClass tClass = tClassDao.getReferenceById(clsId);
        Course course = courseDao.getReferenceById(courseId);

        // 调用模型层业务逻辑
        tClass.assignCourse(course);

        tClassDao.save(tClass);
        return course;
    }

    /**
     * 查看教学班花名册
     */
    public List<Student> listStudents(Long clsId) {
        TClass tClass = tClassDao.getReferenceById(clsId);
        // 调用模型层逻辑，从Selection集合中提取Student
        return tClass.listStudents();
    }
}