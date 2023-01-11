package com.spring.boot.post.presentation.dto.response;

import com.spring.boot.post.domain.image.PostImage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PostImageResponse {

  private Long id;
  private String imagePath;

  private PostImageResponse(Long id, String imagePath) {
    this.id = id;
    this.imagePath = imagePath;
  }

  public static List<PostImageResponse> from(List<PostImage> postImages) {
    return postImages
        .stream()
        .map(postImage -> new PostImageResponse(postImage.getId(), postImage.getImagePath()))
        .collect(Collectors.toList());
  }
}