package com.example.label.dto.label;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author jack
 * @date 2022-06-13
 */
@Data
public class ImportExportDTO {
  /**
   * 标签编号
   */
  @ExcelProperty(index = 0)
  private String code;

  /**
   * 标签全名
   */
  @ExcelProperty(index = 1)
  private String fullTagName;

  /**
   * 父标签编号
   */
  @ExcelProperty(index = 2)
  private String parentCode;

  /**
   * 标签权限
   */
  @ExcelProperty(index = 3)
  private String auth;

  /**
   * 标签状态
   */
  @ExcelProperty(index = 4)
  private String status;
}
