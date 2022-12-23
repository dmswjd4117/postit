package com.spring.boot.image.application.dto;

import lombok.Builder;

@Builder
public class ImageInfoDto {
  private Long id;
  private String imagePath;
}
