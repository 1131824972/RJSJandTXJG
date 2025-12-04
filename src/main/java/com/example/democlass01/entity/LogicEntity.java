package com.example.democlass01.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class LogicEntity extends BaseEntity {

    // 逻辑删除时间（删除时填充，默认null）
    @Column(insertable = false)  // 插入时不赋值
    protected Date deletedTime;

    // 逻辑删除标记：0=未删，1=已删（默认0）
    @Column(insertable = false, nullable = false, columnDefinition = "INT default 0")
    protected Integer isDeleted = 0;
}