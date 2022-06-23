package com.example.label.util;

import com.example.label.dto.label.ImportExportDTO;
import com.example.label.entity.Label;
import com.example.label.enums.LabelAuth;
import com.example.label.enums.LabelStatus;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** @author jack */
public class Labels {

  private static Pattern labelNamePattern = Pattern.compile("^((?!['/\\\\:*?\"<>|]).){1,32}$");

  public static String generateCode() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  /**
   * 转换
   *
   * @param label label
   * @return importExportDto
   */
  public static ImportExportDTO convert(Label label) {
    ImportExportDTO dto = new ImportExportDTO();
    dto.setCode(label.getCode());
    dto.setFullName(label.getName());
    dto.setDescription(label.getDescription());
    dto.setDeptCode(label.getDeptCode());
    LabelAuth auth = LabelAuth.getInstance(label.getAuth());
    if (auth != null) {
      dto.setAuth(auth.getDesc());
    }
    LabelStatus status = LabelStatus.getInstance(label.getStatus());
    if (status != null) {
      dto.setStatus(status.getDesc());
    }
    return dto;
  }

  /**
   * 批量转换
   *
   * @param labels labels
   * @return importExportDtoList
   */
  public static List<ImportExportDTO> convert(List<Label> labels) {
    return labels.stream().map(Labels::convert).collect(Collectors.toList());
  }

  /**
   * 获取父标签全名
   *
   * @param fullName fullName
   * @return parent full name
   */
  public static String getParentFullName(String fullName) {
    int i = fullName.lastIndexOf("/");
    return fullName.substring(0, Math.max(0, i));
  }

  public static String getName(String fullName) {
    int i = fullName.lastIndexOf("/");
    return fullName.substring(i + 1);
  }

  /**
   * determine if name is regular
   *
   * @param name label name
   * @return true if is regular. otherwise false
   */
  public static boolean isRegularName(String name) {
    return labelNamePattern.matcher(name).matches();
  }

  private Labels() {
    throw new AssertionError();
  }
}
