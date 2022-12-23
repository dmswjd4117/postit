package com.spring.boot.tag.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TagInfoDto {
  private Long id;
  private String name;
}
