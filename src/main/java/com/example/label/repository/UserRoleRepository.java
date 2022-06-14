package com.example.label.repository;

import com.example.label.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author xiabiao
 * @date 2022-06-14
 */
public interface UserRoleRepository extends CrudRepository<UserRole, String> {
  /**
   * 用户名查找用户角色关系
   *
   * @param username username
   * @return 用户角色关系
   */
  List<UserRole> findByUsername(String username);
}
