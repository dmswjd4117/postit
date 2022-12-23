package com.spring.boot.post.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostImageInfoDto {
  private Long id;
  private String imagePath;
}
