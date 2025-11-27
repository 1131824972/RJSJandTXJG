package com.example.democlass01.service;

import com.example.democlass01.entity.Selection;
import com.example.democlass01.entity.Student;
import com.example.democlass01.entity.TClass;
import com.example.democlass01.repository.StudentDao;
import com.example.democlass01.repository.TClassDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentService extends LogicService<Student, Long> {

    private final StudentDao studentDao;

    @Resource
    private TClassDao tClassDao; // 需要用到教学班DAO来查找班级

    public StudentService(@Autowired StudentDao dao) {
        super(dao);
        this.studentDao = dao;
    }

    /**
     * 学生选课业务
     * @param stuId 学生ID
     * @param clsId 教学班ID
     * @return 选课记录
     */
    @Transactional // 选课涉及修改数据库，必须开启事务
    public Selection selectClass(Long stuId, Long clsId) {
        // 1. 从数据库获取学生和教学班的实体（使用getReferenceById获取代理对象，性能更好）
        Student student = studentDao.getReferenceById(stuId);
        TClass tClass = tClassDao.getReferenceById(clsId);

        // 2. 调用模型层的业务逻辑（这是你在实验2中写的代码！）
        // 这里的逻辑包括：判断满员、判断重复、建立关联等
        Selection selection = student.selectClass(tClass);

        // 3. 如果选课成功（selection不为null），保存学生实体
        // 因为配置了CascadeType.ALL，保存学生时会自动级联保存Selection记录
        if (selection != null) {
            studentDao.save(student);
        }

        return selection;
    }

    /**
     * 学生退课业务
     */
    @Transactional
    public Selection withdraw(Long stuId, Long clsId) {
        Student student = studentDao.getReferenceById(stuId);
        TClass tClass = tClassDao.getReferenceById(clsId);

        // 调用模型层的退课逻辑
        Selection selection = student.withdraw(tClass);

        // 级联保存，OrphanRemoval=true 会自动删除那条Selection记录
        if (selection != null) {
            studentDao.save(student);
        }
        return selection;
    }

    /**
     * 获取我的课程表（调用实验3写在DAO里的自定义查询）
     */
    public List<TClass> getMyClasses(Long stuId) {
        Student student = studentDao.getReferenceById(stuId);
        // 调用我们在DAO层写的 @Query 方法
        return studentDao.getMyClasses(student);
    }
}