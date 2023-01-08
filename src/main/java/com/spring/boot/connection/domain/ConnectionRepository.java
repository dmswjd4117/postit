package com.spring.boot.connection.domain;

import com.spring.boot.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

  @Query("select c from Connection c" +
      " where c.member = :member" +
      " and c.targetMember = :targetMember")
  Optional<Connection> findByMemberAndTargetMember(
      @Param("member") Member member,
      @Param("targetMember") Member targetMember
  );
}