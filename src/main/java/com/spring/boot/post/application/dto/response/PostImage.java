package com.spring.boot.post.application.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PostImage {

  private Long id;
  private String imagePath;

  private PostImage(Long id, String imagePath) {
    this.id = id;
    this.imagePath = imagePath;
  }

  public static List<PostImage> from(List<com.spring.boot.post.domain.image.PostImage> postImages) {
    return postImages
        .stream()
        .map(postImage -> new PostImage(postImage.getId(), postImage.getImagePath()))
        .collect(Collectors.toList());
  }
}