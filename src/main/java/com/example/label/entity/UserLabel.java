package com.example.label.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 用户可以使用的标签。
 *
 * @author xiabiao
 * @date 2022-06-16
 */
@Data
public class UserLabel {

  @Id
  private Long id;

  private String username;

  private String labelCode;

  private Date createTime;
}
