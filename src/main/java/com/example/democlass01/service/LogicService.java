package com.example.democlass01.service;

import com.example.democlass01.entity.LogicEntity;
import com.example.democlass01.repository.LogicDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

// 泛型服务基类，T是实体类型，ID是主键类型
public abstract class LogicService<T extends LogicEntity, ID extends Serializable> {

    // 这是一个抽象的DAO引用，具体的Service在构造时传入具体的DAO
    protected LogicDAO<T, ID> logicDAO;

    public LogicService(LogicDAO<T, ID> logicDAO) {
        this.logicDAO = logicDAO;
    }

    // 获取具体的DAO，供子类使用
    protected LogicDAO<T, ID> getDAO() {
        return logicDAO;
    }

    // --- 基础 CRUD 操作 ---

    // 根据ID获取实体
    public T get(ID id) {
        // getReferenceById是JPA的新API，对应以前的getOne
        return logicDAO.getReferenceById(id);
    }

    // 获取所有实体
    public List<T> getAll() {
        return logicDAO.findAll();
    }

    // 分页获取所有实体
    public Page<T> getAll(int page, int size) {
        // 按照创建时间倒序排列
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        return logicDAO.findAll(pageable);
    }

    // 保存或更新实体 (新增/修改)
    @Transactional // 开启事务，保证写入安全
    public T save(T entity) {
        return logicDAO.save(entity);
    }

    // 删除实体
    @Transactional
    public void delete(T entity) {
        logicDAO.delete(entity);
    }

    // 根据ID删除实体
    @Transactional
    public void delete(ID id) {
        logicDAO.deleteById(id);
    }
}