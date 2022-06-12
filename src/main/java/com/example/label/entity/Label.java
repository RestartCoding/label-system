package com.example.label.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Label {

    @Id
    @ExcelIgnore
    private Long id;

    @ExcelProperty(index = 0)
    private String code;

    @ExcelProperty(index = 1)
    private String name;

    @ExcelProperty(index = 2)
    private String parentCode;

    @ExcelProperty(index = 3)
    private Integer auth;

    @ExcelProperty(index = 4)
    private Integer status;

    @ExcelIgnore
    private Date createTime;

    @ExcelIgnore
    private Date updateTime;
}
