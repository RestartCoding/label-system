package com.example.label.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author xiabiao
 * @date 2022-06-14
 */
@Data
public class UserRole {

  @Id
  private Long id;

  private String username;

  private String roleCode;

  private Date createTime;
}
