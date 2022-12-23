package com.spring.boot.post.application.dto;

import com.spring.boot.member.application.dto.MemberInfoDto;
import lombok.Builder;

@Builder
public class PostLikeInfoDto {
  private Long id;
  private MemberInfoDto memberInfoDto;
}
