package com.example.democlass01.service;

import com.example.democlass01.entity.Selection;
import com.example.democlass01.entity.Student;
import com.example.democlass01.entity.TClass;
import com.example.democlass01.entity.Teacher;
import com.example.democlass01.repository.SelectionDao;
import com.example.democlass01.repository.StudentDao;
import com.example.democlass01.repository.TClassDao;
import com.example.democlass01.repository.TeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TeacherService extends LogicService<Teacher, Long> {

    @Resource
    private TeacherDao teacherDao;
    @Resource
    private TClassDao tClassDao;
    @Resource
    private StudentDao studentDao;
    @Resource
    private SelectionDao selectionDao; // 打分需要保存 Selection

    public TeacherService(@Autowired TeacherDao dao) {
        super(dao);
        this.teacherDao = dao;
    }

    /**
     * 教师给学生录入期中成绩
     *
     * @param teacherId 教师ID
     * @param clsId     教学班ID
     * @param stuId     学生ID
     * @param score     分数
     * @return 打分后的选课记录
     */
    @Transactional
    public Selection rankMidScore(Long teacherId, Long clsId, Long stuId, Double score) {
        Teacher teacher = teacherDao.getReferenceById(teacherId);
        TClass tClass = tClassDao.getReferenceById(clsId);

        // 验证教师权限
        if (tClass.getTeacher() == null || !tClass.getTeacher().getId().equals(teacherId)) {
            System.out.println("操作失败：该教师不是该教学班的任课教师");
            return null;
        }

        // 直接通过 DAO 查询选课记录，而不是遍历 tClass.getSelections()
        // 这样即使内存中的 tClass 对象没有更新，也能查到数据
        Selection targetSelection = selectionDao.findByStuIdAndClsId(stuId, clsId);

        if (targetSelection != null) {
            targetSelection.setMidScore(score);
            return selectionDao.save(targetSelection);
        }

        return null;
    }
}