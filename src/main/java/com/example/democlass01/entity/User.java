package com.example.democlass01.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")  // 区分学生/教师
@Where(clause = "is_deleted=0")
public abstract class User extends LogicEntity {

    @Column(length = 50)
    protected String username;  // 用户名

    @Column(length = 100)
    protected String password;  // 密码

    @Column(updatable = false, insertable = false) //不可更新、不可插入
    protected Integer type;
}