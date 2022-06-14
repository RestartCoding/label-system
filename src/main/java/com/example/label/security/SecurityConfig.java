package com.example.label.security;

import com.example.label.entity.RoleOperation;
import com.example.label.entity.User;
import com.example.label.entity.UserRole;
import com.example.label.filter.JwtFilter;
import com.example.label.repository.RoleOperationRepository;
import com.example.label.repository.UserRepository;
import com.example.label.repository.UserRoleRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
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

  @Resource private UserRoleRepository userRoleRepository;

  @Resource private RoleOperationRepository roleOperationRepository;

  @Resource
  private JwtFilter jwtFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin().permitAll();
    http.authorizeRequests().antMatchers("/jwt/**").permitAll();
    http.authorizeRequests().anyRequest().authenticated();
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
  protected AuthenticationManager authenticationManager() throws Exception {
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

        List<GrantedAuthority> authorities = new ArrayList<>();
        // 查询用户角色
        List<UserRole> userRoles = userRoleRepository.findByUsername(username);
        List<String> roleCodes = new ArrayList<>();
        for (UserRole userRole : userRoles) {
          roleCodes.add(userRole.getRoleCode());
          authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRoleCode()));
        }
        // 查询角色操作
        List<RoleOperation> roleOperations = roleOperationRepository.findAllByRoleCodeIn(roleCodes);
        for (RoleOperation roleOperation : roleOperations) {
          authorities.add(new SimpleGrantedAuthority(roleOperation.getOperationCode()));
        }
        // 设置权限
        return new UsernamePasswordAuthenticationToken(username, password, authorities);
      }
      throw new InsufficientAuthenticationException("Can not authenticate.");
    };
  }

  @Bean
  public Key jwtSecretKey() {
    return Keys.secretKeyFor(SignatureAlgorithm.HS512);
  }
}
