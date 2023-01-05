package com.spring.boot.post.application.dto.response;

import com.spring.boot.post.domain.image.PostImage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PostImageResponseDto {

  private Long id;
  private String imagePath;

  private PostImageResponseDto(Long id, String imagePath) {
    this.id = id;
    this.imagePath = imagePath;
  }

  public static List<PostImageResponseDto> from(List<PostImage> postImages) {
    return postImages
        .stream()
        .map(postImage -> new PostImageResponseDto(postImage.getId(), postImage.getImagePath()))
        .collect(Collectors.toList());
  }
}