package com.example.democlass01.controller;

import com.example.democlass01.entity.LogicEntity;
import com.example.democlass01.service.LogicService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

// 泛型控制器基类，S是Service类型，T是实体类型，ID是主键类型
public abstract class BaseController<S extends LogicService<T, ID>, T extends LogicEntity, ID extends Serializable> {

    protected S service;

    public BaseController(S service) {
        this.service = service;
    }

    @Operation(summary = "通过ID获取实体")
    @GetMapping("/get")
    public T get(@RequestParam ID id) {
        return service.get(id);
    }

    @Operation(summary = "获取所有实体")
    @GetMapping("/getall")
    public List<T> getAll() {
        return service.getAll();
    }

    @Operation(summary = "分页获取所有实体")
    @GetMapping("/getallPage")
    public Page<T> getAll(@RequestParam int page, @RequestParam int size) {
        return service.getAll(page, size);
    }

    @Operation(summary = "创建实体")
    @PostMapping("/create")
    public T create(@RequestBody T entity) {
        return service.save(entity);
    }

    @Operation(summary = "修改实体")
    @PostMapping("/update")
    public T update(@RequestBody T entity) {
        return service.save(entity);
    }

    @Operation(summary = "通过ID删除实体")
    @GetMapping("/delete") // 为了演示方便使用Get，严格RESTful应用DELETE方法
    public void delete(@RequestParam ID id) {
        service.delete(id);
    }
}