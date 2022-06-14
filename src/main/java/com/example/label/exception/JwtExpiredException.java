package com.example.label.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author xiabiao
 * @date 2022-06-14
 */
public class JwtExpiredException extends AuthenticationException {
  public JwtExpiredException(String msg, Throwable t) {
    super(msg, t);
  }

  public JwtExpiredException(String msg) {
    super(msg);
  }
}
