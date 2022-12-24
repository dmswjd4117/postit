package com.spring.boot.member.presentaion.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberResponse {
  private Long id;
  private String email;
  private String profileImagePath;
}
