package com.example.label.dto.label;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

/**
 * @author jack
 * @date 2022-06-13
 */
@Data
public class ImportExportDTO {
  /** 标签编号 */
  @ExcelProperty(index = 0)
  private String code;

  /** 标签全名 */
  @ExcelProperty(index = 1)
  private String fullName;

  /** 标签权限 */
  @ExcelProperty(index = 2)
  private String auth;

  /** 标签状态 */
  @ExcelProperty(index = 3)
  private String status;

  /** 描述 */
  @ExcelProperty(index = 4)
  private String description;

  /** 所属部门 */
  @ExcelProperty(index = 5)
  private String deptName;

  /** 部门编号 */
  @ExcelProperty(index = 6)
  private String deptCode;

  @JsonView(ImportResultView.class)
  @ExcelIgnore
  private Integer lineNum;

  @ExcelIgnore
  @JsonView(ImportResultView.class)
  private String msg;

  public static class ImportResultView {}
}
