package com.spring.boot.like.application.dto;

import lombok.Getter;

@Getter
public class LikeDto {

  private int likeCount;
  private boolean liked;

  public LikeDto(int likeCount, boolean liked) {
    this.likeCount = likeCount;
    this.liked = liked;
  }

}
