package com.example.label.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author xiabiao
 * @date 2022-06-16
 */
public class UserUtils {

  /**
   * 获取当前用户名
   *
   * @return username
   */
  public static String currUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      return (String) authentication.getPrincipal();
    }
    return null;
  }

  private UserUtils() {
    throw new AssertionError();
  }
}
