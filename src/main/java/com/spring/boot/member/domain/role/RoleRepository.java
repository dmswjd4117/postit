package com.spring.boot.member.domain.role;

import com.spring.boot.member.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String name);
}
