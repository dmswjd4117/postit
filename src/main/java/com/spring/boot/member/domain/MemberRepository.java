package com.spring.boot.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  @Query("select m from Member m join fetch m.role where m.email = :email")
  Optional<Member> findByEmailWithRole(String email);
}
