package com.example.label.repository;

import com.example.label.entity.RoleOperation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author xiabiao
 * @date 2022-06-14
 */
public interface RoleOperationRepository extends CrudRepository<RoleOperation, String> {
  /**
   * 角色编号查询角色操作关系
   *
   * @param roleCodes roleCodes
   * @return 角色操作关系
   */
  List<RoleOperation> findAllByRoleCodeIn(List<String> roleCodes);
}
