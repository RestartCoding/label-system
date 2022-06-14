package com.example.label.repository;

import com.example.label.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * @author xiabiao
 * @date 2022-06-14
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  /**
   * 用户名查询
   *
   * @param username username
   * @return user
   */
  Optional<User> findByUsername(String username);
}
