package com.spring.boot.post.presentaion.dto;

import com.spring.boot.image.domain.Image;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PostImageResponse {

  private Long id;
  private String imagePath;

  public PostImageResponse(Long id, String imagePath) {
    this.id = id;
    this.imagePath = imagePath;
  }

  public static PostImageResponse from(Image image) {
    return new PostImageResponse(image.getId(), image.getImagePath());
  }

  public static List<PostImageResponse> from(List<Image> images) {
    return images
        .stream()
        .map(PostImageResponse::from)
        .collect(Collectors.toList());
  }

}
