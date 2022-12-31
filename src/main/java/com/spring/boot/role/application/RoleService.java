package com.spring.boot.role.application;

import com.spring.boot.role.domain.Role;
import com.spring.boot.role.domain.RoleName;
import com.spring.boot.role.domain.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  private final RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public Role getRole(RoleName roleName) {
    return roleRepository.findByRoleName(roleName.getValue())
        .orElseGet(()->roleRepository.save(new Role(RoleName.MEMBER.getValue(), "member role")));
  }
}
