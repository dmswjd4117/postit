package com.spring.boot.member.domain.role;

import com.spring.boot.member.domain.role.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
}
