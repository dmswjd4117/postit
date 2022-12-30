package com.spring.boot.sampleData;

import com.spring.boot.user.domain.User;
import com.spring.boot.role.application.RoleService;
import com.spring.boot.role.domain.Role;
import com.spring.boot.role.domain.RoleName;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
public class initUser {

  private final InitMemberService initMemberService;

  public initUser(InitMemberService initMemberService) {
    this.initMemberService = initMemberService;
  }

  @PostConstruct
  public void init() {
    initMemberService.init();
  }

  @Component
  static class InitMemberService {

    private final EntityManager entityManager;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    InitMemberService(EntityManager entityManager, RoleService roleService,
        PasswordEncoder passwordEncoder) {
      this.entityManager = entityManager;
      this.roleService = roleService;
      this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void init() {
      Role role = roleService.getRole(RoleName.MEMBER);
      for (int i = 0; i < 10; i++) {
        User user = new User(i + "email@gmail.com", passwordEncoder.encode("password"), "name", role);
        entityManager.persist(user);
      }
    }
  }
}
