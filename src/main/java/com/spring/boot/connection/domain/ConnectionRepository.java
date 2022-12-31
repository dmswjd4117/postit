package com.spring.boot.connection.domain;

import com.spring.boot.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

  @Query("select c from Connection c" +
      " where c.user = :user" +
      " and c.targetUser = :targetUser")
  Optional<Connection> findByMemberAndTargetMember(
      @Param("user") User user,
      @Param("targetUser") User targetUser
  );
}