package com.spring.boot.common.exception;

import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;

public class MemberNotFoundException extends NotFoundException{

  public MemberNotFoundException(Long memberId) {
    super(Member.class, "존재하지 않는 유저입니다.", "아이디:", memberId);
  }
}
