package com.example.democlass01.controller;

import com.example.democlass01.entity.Selection;
import com.example.democlass01.entity.Teacher;
import com.example.democlass01.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "教师实体的控制器")
@RestController
@RequestMapping("/teacher")
public class TeacherController extends BaseController<TeacherService, Teacher, Long> {

    @Autowired
    public TeacherController(TeacherService service) {
        super(service);
    }

    @Operation(summary = "评分-期中成绩")
    @GetMapping("/midscore")
    public Selection rankMidScore(
            @Parameter(description = "教师ID") @RequestParam Long t_id,
            @Parameter(description = "教学班ID") @RequestParam Long cls_id,
            @Parameter(description = "学生ID") @RequestParam Long stu_id,
            @Parameter(description = "期中成绩") @RequestParam Double score) {
        return service.rankMidScore(t_id, cls_id, stu_id, score);
    }
}