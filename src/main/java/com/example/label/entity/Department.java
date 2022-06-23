package com.example.label.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author xiabiao
 * @date 2022-06-23
 */
@Data
public class Department {

  private Long id;

  private String code;

  private String parentCode;

  private String name;

  private Date createTime;

  private Date updateTime;
}
