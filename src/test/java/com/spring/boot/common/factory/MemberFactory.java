package com.spring.boot.common.factory;

import com.spring.boot.common.mock.MockMember;
import com.spring.boot.member.domain.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberFactory {

  public static Member createMember(String email, String name, String password) {
    return MockMember.builder(email, name, password)
        .email(email)
        .build();
  }
}
