package com.example.label.entity;

import com.example.label.enums.LabelAuth;
import com.example.label.enums.LabelStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Label {

    @Id
    private Long id;

    private String name;

    private String code;

    private String parentCode;

    private Integer status;

    private Integer auth;

    private Date createTime;

    private Date updateTime;
}
