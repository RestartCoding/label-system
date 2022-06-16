package com.example.label.service;

import com.example.label.dto.user.label.ShareLabelDTO;

/**
 * @author xiabiao
 * @date 2022-06-16
 */
public interface UserLabelService {

  /**
   * 授权标签给其他用户使用。只有授权类型标签才可以授权给其他用户。
   *
   * @implNote 注意删除标签原有授权用户
   * @param shareLabelDTO shareLabelDTO
   */
  void share(ShareLabelDTO shareLabelDTO);
}
