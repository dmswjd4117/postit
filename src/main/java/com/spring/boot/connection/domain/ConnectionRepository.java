package com.spring.boot.connection.domain;

import com.spring.boot.member.domain.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConnectionRepository extends JpaRepository<Connections, Long> {

  @Query("select c from Connections c" +
      " where c.member = :member" +
      " and c.targetMember = :targetMember")
  Optional<Connections> findByMemberAndTargetMember(
      @Param("member") Member member,
      @Param("targetMember") Member targetMember
  );
}