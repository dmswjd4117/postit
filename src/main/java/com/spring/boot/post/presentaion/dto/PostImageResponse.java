package com.spring.boot.post.presentaion.dto;

import com.spring.boot.image.domain.PostImage;
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

  public static PostImageResponse from(PostImage postImage) {
    return new PostImageResponse(postImage.getId(), postImage.getImagePath());
  }

  public static List<PostImageResponse> from(List<PostImage> postImages) {
    return postImages
        .stream()
        .map(PostImageResponse::from)
        .collect(Collectors.toList());
  }

}
