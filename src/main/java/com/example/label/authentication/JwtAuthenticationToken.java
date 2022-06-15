package com.example.label.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author xiabiao
 * @date 2022-06-15
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
  public JwtAuthenticationToken(Object principal, Object credentials) {
    super(principal, credentials);
  }

  public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }
}
