package com.spring.boot.member.presentaion.dto;

import com.spring.boot.member.domain.member.Member;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberResponse {

  private Long id;
  private String email;
  private String profileImagePath;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;

  public static MemberResponse from(Member member) {
    return MemberResponse.builder()
        .id(member.getId())
        .email(member.getEmail())
        .profileImagePath(member.getProfileImagePath())
        .createdDate(member.getCreatedDate())
        .modifiedDate(member.getModifiedDate())
        .build();
  }
}
