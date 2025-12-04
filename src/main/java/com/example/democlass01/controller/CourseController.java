package com.example.democlass01.controller;

import com.example.democlass01.entity.Course;
import com.example.democlass01.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "课程实体的控制器")
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController<CourseService, Course, Long> {

    @Autowired
    public CourseController(CourseService service) {
        super(service);
    }
}