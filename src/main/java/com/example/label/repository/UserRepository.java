package com.example.label.repository;

import com.example.label.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
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

  /**
   * 用户名批量查询
   *
   * @param usernameList usernameList
   * @return user list
   */
  List<User> findAllByUsernameIn(List<String> usernameList);
}
