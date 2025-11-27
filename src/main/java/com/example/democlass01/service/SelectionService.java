package com.example.democlass01.service;

import com.example.democlass01.entity.Selection;
import com.example.democlass01.repository.SelectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectionService extends LogicService<Selection, Long> {

    // 虽然 LogicService 里已经有了泛型的 dao，但这里保留具体的 selectionDao 引用方便后续扩展特定查询
    private final SelectionDao selectionDao;

    public SelectionService(@Autowired SelectionDao dao) {
        super(dao); // 将具体的 DAO 传给基类 LogicService
        this.selectionDao = dao;
    }

    // 目前阶段，Selection 的核心业务逻辑（如选课、退课）被分配到了 StudentService 中，
    // 成绩录入逻辑被分配到了 TeacherService 中。
    // 所以这里暂时只需要继承 LogicService 提供的基础 CRUD 方法即可。
}