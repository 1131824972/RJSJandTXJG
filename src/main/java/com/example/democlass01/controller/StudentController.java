package com.example.democlass01.controller;

import com.example.democlass01.dto.StudentDTO;
import com.example.democlass01.entity.Selection;
import com.example.democlass01.entity.Student;
import com.example.democlass01.entity.TClass;
import com.example.democlass01.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "学生实体的控制器")
@RestController
@RequestMapping("/student")
public class StudentController extends BaseController<StudentService, Student, Long> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public StudentController(StudentService service) {
        super(service);
    }

    @Operation(summary = "学生选课")
    @GetMapping("/select")
    public Selection select(
            @Parameter(description = "学号") @RequestParam Long stu_id,
            @Parameter(description = "教学班号") @RequestParam Long cls_id) {
        return service.selectClass(stu_id, cls_id);
    }

    @Operation(summary = "学生退课")
    @GetMapping("/withdraw")
    public Selection withdraw(
            @Parameter(description = "学号") @RequestParam Long stu_id,
            @Parameter(description = "教学班号") @RequestParam Long cls_id) {
        return service.withdraw(stu_id, cls_id);
    }

    @Operation(summary = "我的课程表")
    @GetMapping("/myClasses")
    public List<TClass> getMyClasses(@Parameter(description = "学号") @RequestParam Long stu_id) {
        return service.getMyClasses(stu_id);

    }
    @Operation(summary = "获取所有实体 (DTO版)")
    @GetMapping("/getall_dto")
    public List<StudentDTO> getAllDTO() {
        // 1. 从 Service 获取所有实体 (包含代理对象)
        List<Student> students = service.getAll();

        // 2. 使用 ModelMapper 将 Entity 转换为 DTO
        // 这步转换会把数据从 Hibernate 代理对象里“倒”出来，放入纯净的 DTO 对象中
        return students.stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }
}