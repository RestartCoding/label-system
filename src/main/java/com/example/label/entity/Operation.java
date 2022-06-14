package com.example.label.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author xiabiao
 * @date 2022-06-14
 */
@Data
public class Operation {

  @Id
  private Long id;

  private String code;

  private String name;

  private String description;

  private Date createTime;

  private Date updateTime;
}
