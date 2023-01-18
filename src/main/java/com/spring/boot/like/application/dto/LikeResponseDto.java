package com.spring.boot.like.application.dto;

import lombok.Getter;

@Getter
public class LikeResponseDto {

  private int likeCount;
  private boolean liked;

  public LikeResponseDto(int likeCount, boolean liked) {
    this.likeCount = likeCount;
    this.liked = liked;
  }

}
