package com.spring.boot.image.application.dto;

import com.spring.boot.post.domain.image.Image;
import java.util.List;
import java.util.stream.Collectors;

public class ImageDtoMapper {

  public static ImageInfoDto imageInfoDto(Image image){
    return ImageInfoDto.builder()
        .id(image.getId())
        .imagePath(image.getImagePath())
        .build();
  }

  public static List<ImageInfoDto> imageInfoDtos(List<Image> images) {
    return images.stream()
        .map(ImageDtoMapper::imageInfoDto)
        .collect(Collectors.toList());
  }
}
