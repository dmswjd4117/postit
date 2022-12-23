package com.spring.boot.member.presentaion.dto.response;

import com.spring.boot.member.domain.Member;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberResponse {

  private Long id;
  private String email;
  private String profileImagePath;

}
