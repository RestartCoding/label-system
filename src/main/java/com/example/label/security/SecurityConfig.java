package com.example.label.security;

import com.example.label.entity.User;
import com.example.label.filter.JwtFilter;
import com.example.label.repository.UserRepository;
import com.example.label.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

/**
 * @author xiabiao
 * @date 2022-06-13
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Resource private UserRepository userRepository;

  @Resource private UserService userService;

  @Resource private JwtFilter jwtFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin().permitAll();
    http.authorizeRequests().antMatchers("/jwt/**", "/h2-console/**").permitAll();
    http.authorizeRequests().anyRequest().authenticated();
    // can not use h2-console if do not do this
    http.headers().frameOptions().disable();
    http.csrf().disable();

    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    try {
      return new BCryptPasswordEncoder(
          BCryptPasswordEncoder.BCryptVersion.$2Y, SecureRandom.getInstanceStrong());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected AuthenticationManager authenticationManager() {
    return (authentication) -> {
      if (authentication instanceof UsernamePasswordAuthenticationToken) {
        UsernamePasswordAuthenticationToken token =
            (UsernamePasswordAuthenticationToken) authentication;
        String username = (String) token.getPrincipal();
        String password = (String) token.getCredentials();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()
            || !passwordEncoder().matches(password, optionalUser.get().getPassword())) {
          throw new UsernameNotFoundException("Incorrect username or password.");
        }

        // 设置权限
        return new UsernamePasswordAuthenticationToken(
            username, password, userService.getAuthorities(username));
      }
      throw new InsufficientAuthenticationException("Can not authenticate.");
    };
  }
}
