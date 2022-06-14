package com.example.label.security;

import com.example.label.entity.RoleOperation;
import com.example.label.entity.User;
import com.example.label.entity.UserRole;
import com.example.label.repository.RoleOperationRepository;
import com.example.label.repository.UserRepository;
import com.example.label.repository.UserRoleRepository;
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

import javax.annotation.Resource;
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

  @Resource private PasswordEncoder passwordEncoder;

  @Resource private UserRoleRepository userRoleRepository;

  @Resource private RoleOperationRepository roleOperationRepository;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin().permitAll();
    http.csrf().disable();
    http.authorizeRequests().anyRequest().authenticated();
  }

  @Bean
  public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
    return new BCryptPasswordEncoder(
        BCryptPasswordEncoder.BCryptVersion.$2Y, SecureRandom.getInstanceStrong());
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
            || !passwordEncoder.matches(password, optionalUser.get().getPassword())) {
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
}
