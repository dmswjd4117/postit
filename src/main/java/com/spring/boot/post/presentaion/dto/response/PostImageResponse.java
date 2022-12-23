package com.spring.boot.post.presentaion.dto.response;

import lombok.Getter;

@Getter
public class PostImageResponse {

  private Long id;
  private String imagePath;

  public PostImageResponse(Long id, String imagePath) {
    this.id = id;
    this.imagePath = imagePath;
  }


}
