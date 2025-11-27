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
     * @param teacherId 教师ID
     * @param clsId 教学班ID
     * @param stuId 学生ID
     * @param score 分数
     * @return 打分后的选课记录
     */
    @Transactional
    public Selection rankMidScore(Long teacherId, Long clsId, Long stuId, Double score) {
        // 1. 获取相关实体
        Teacher teacher = teacherDao.getReferenceById(teacherId);
        TClass tClass = tClassDao.getReferenceById(clsId);
        Student student = studentDao.getReferenceById(stuId);

        // 2. 验证该教学班是否属于该教师（安全性检查）
        // 注意：这里需要 TClass 实体中有 getTeacher() 方法，且 Teacher 实体正确加载
        // 简单判断：
        if (tClass.getTeacher() == null || !tClass.getTeacher().getId().equals(teacherId)) {
            // 可以在这里抛出异常，或者返回 null
            System.out.println("操作失败：该教师不是该教学班的任课教师");
            return null;
        }

        // 3. 获取该学生在该班级的选课记录
        // 这里利用了我们在 Selection 实体中定义的关联，或者使用 StudentDao 的自定义查询
        // 但更直接的方式是直接从 TClass 的 selections 集合里找，或者用 SelectionDao 查
        // 为了演示充血模型，我们假设 Teacher 并没有直接的方法去操作 Selection，而是通过业务逻辑找到 Selection

        Selection targetSelection = null;
        // 遍历班级选课记录找到该学生 (数据量大时建议在 DAO 写 @Query 查询)
        for (Selection sel : tClass.getSelections()) {
            if (sel.getStudent().getId().equals(stuId)) {
                targetSelection = sel;
                break;
            }
        }

        if (targetSelection != null) {
            // 4. 设置成绩
            targetSelection.setMidScore(score);
            // 5. 保存更新
            return selectionDao.save(targetSelection);
        }

        return null;
    }
}