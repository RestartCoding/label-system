package com.example.label.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author jack
 */
@Data
public class Label {

    @Id
    private Long id;

    private String code;

    private String name;

    private String parentCode;

    private Integer auth;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
