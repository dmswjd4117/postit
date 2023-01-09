package com.spring.boot.post.application.dto.response;

import com.spring.boot.post.domain.image.PostImage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PostImageDto {

  private Long id;
  private String imagePath;

  private PostImageDto(Long id, String imagePath) {
    this.id = id;
    this.imagePath = imagePath;
  }

  public static List<PostImageDto> from(List<PostImage> postImages) {
    return postImages
        .stream()
        .map(postImage -> new PostImageDto(postImage.getId(), postImage.getImagePath()))
        .collect(Collectors.toList());
  }
}