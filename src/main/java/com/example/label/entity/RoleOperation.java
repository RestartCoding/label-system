package com.example.label.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author xiabiao
 * @date 2022-06-14
 */
@Data
public class RoleOperation {

  @Id
  private String roleCode;

  private String operationCode;

  private Date createTime;
}
