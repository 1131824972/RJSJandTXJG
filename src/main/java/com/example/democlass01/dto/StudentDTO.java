package com.example.democlass01.dto;

import lombok.Data;

@Data
public class StudentDTO {
    // 只需要定义你想展示给前端的字段
    private Long id;
    private String studentName;
    private String major;
    private String grade;
    private String school;
    private String username;

    // 注意：这里我们故意【不包含】 List<Selection> selections
    // 1. 避免了死循环引用 (Student -> Selection -> Student ...)
    // 2. 彻底解决了 Hibernate 代理对象序列化报错
    // 3. 如果前端需要选课记录，应该调用单独的接口 (如 /student/myClasses)
}