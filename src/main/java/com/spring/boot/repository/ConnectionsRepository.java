package com.spring.boot.repository;

import com.spring.boot.domain.connection.Connections;
import com.spring.boot.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConnectionsRepository extends JpaRepository<Connections, Long> {

    @Query("select c from Connections c" +
            " where c.member = :member" +
            " and c.targetMember = :targetMember")
    Optional<Connections> findByMemberAndTargetMember(
            @Param("member") Member member,
            @Param("targetMember") Member targetMember
    );
}
