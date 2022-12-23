package com.spring.boot.post.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostTagInfoDto {
  private Long id;
  private String name;
}
