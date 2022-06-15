package com.example.label.service.impl;

import com.example.label.entity.RoleOperation;
import com.example.label.entity.UserRole;
import com.example.label.repository.RoleOperationRepository;
import com.example.label.repository.UserRoleRepository;
import com.example.label.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiabiao
 * @date 2022-06-15
 */
@Service
public class UserServiceImpl implements UserService {

  @Resource
  private UserRoleRepository userRoleRepository;

  @Resource
  private RoleOperationRepository roleOperationRepository;

  @Override
  public List<GrantedAuthority> getAuthorities(String username) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    List<UserRole> userRoles = userRoleRepository.findByUsername(username);
    List<String> roleCodes = new ArrayList<>();
    for (UserRole e : userRoles) {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + e.getRoleCode()));
      roleCodes.add(e.getRoleCode());
    }

    if (roleCodes.size() > 0){
      List<RoleOperation> roleOperations = roleOperationRepository.findAllByRoleCodeIn(roleCodes);
      for (RoleOperation e : roleOperations) {
        authorities.add(new SimpleGrantedAuthority(e.getOperationCode()));
      }
    }
    return authorities;
  }
}
