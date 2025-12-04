package com.example.democlass01.controller;

import com.example.democlass01.entity.Student;
import com.example.democlass01.entity.TClass;
import com.example.democlass01.entity.Teacher;
import com.example.democlass01.service.TClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "教学班实体的控制器")
@RestController
@RequestMapping("/class")
public class TClassController extends BaseController<TClassService, TClass, Long> {

    @Autowired
    public TClassController(TClassService service) {
        super(service);
    }

    @Operation(summary = "获取教学班花名册")
    @GetMapping("/students")
    public List<Student> listStudents(@Parameter(description = "教学班ID") @RequestParam Long cls_id) {
        return service.listStudents(cls_id);
    }

    @Operation(summary = "分配任课教师")
    @GetMapping("/assignTeacher")
    public Teacher assignTeacher(
            @Parameter(description = "教学班ID") @RequestParam Long cls_id,
            @Parameter(description = "教师ID") @RequestParam Long t_id) {
        return service.assignTeacher(cls_id, t_id);
    }
}