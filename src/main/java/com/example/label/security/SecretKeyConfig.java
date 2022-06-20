package com.example.label.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * @author xiabiao
 * @date 2022-06-20
 */
@Configuration
public class SecretKeyConfig {

  @Value("${jwt.sk}")
  private String jwtSk;

  @Bean
  public Key jwtSecretKey() {
    return Keys.hmacShaKeyFor(jwtSk.getBytes(StandardCharsets.UTF_8));
  }
}
