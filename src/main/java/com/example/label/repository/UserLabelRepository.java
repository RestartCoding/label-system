package com.example.label.repository;

import com.example.label.entity.UserLabel;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author xiabiao
 * @date 2022-06-16
 */
public interface UserLabelRepository extends CrudRepository<UserLabel, Long> {

  /**
   * 标签编号批量移除
   *
   * @param labelCodes labelCodes
   */
  @Modifying
  @Query("delete from user_label where label_code in (:labelCodes)")
  void removeByLabelCodeIn(List<String> labelCodes);
}
