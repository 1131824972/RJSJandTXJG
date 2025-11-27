package com.example.democlass01.service;

import com.example.democlass01.entity.Course;
import com.example.democlass01.repository.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService extends LogicService<Course, Long> {

    public CourseService(@Autowired CourseDao dao) {
        super(dao);
    }
}