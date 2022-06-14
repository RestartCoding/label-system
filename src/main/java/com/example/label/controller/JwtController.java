package com.example.label.controller;

import com.example.label.entity.User;
import com.example.label.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author xiabiao
 * @date 2022-06-14
 */
@RestController
@RequestMapping("/jwt")
public class JwtController {

  private static final int VALID_TIME_MINUTES = 10;

  @Resource private UserRepository userRepository;

  @Resource private PasswordEncoder passwordEncoder;

  @Resource private Key jwtSecretKey;

  @GetMapping("/token")
  public String applyToken(@RequestParam String username, @RequestParam String password) {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    if (!optionalUser.isPresent()
        || !passwordEncoder.matches(password, optionalUser.get().getPassword())) {
      throw new BadCredentialsException("Incorrect username or password.");
    }
    return Jwts.builder()
        .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
        .setAudience(username)
        .setExpiration(
            new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(VALID_TIME_MINUTES)))
        .compact();
  }
}
