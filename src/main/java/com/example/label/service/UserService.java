package com.example.label.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author jack
 * @date 2022-06-15
 */
public interface UserService {
  /**
   * 获取用户的权限
   *
   * @param username username
   * @return authorities
   */
  List<GrantedAuthority> getAuthorities(String username);
}
