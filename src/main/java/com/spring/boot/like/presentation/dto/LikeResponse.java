package com.spring.boot.like.presentation.dto;

import com.spring.boot.like.application.dto.LikeDto;
import lombok.Getter;

@Getter
public class LikeResponse {

  private int likeTotalCount;
  private boolean liked;

  public LikeResponse(int likeTotalCount, boolean liked) {
    this.likeTotalCount = likeTotalCount;
    this.liked = liked;
  }

  public static LikeResponse from(LikeDto likeDto) {
    return new LikeResponse(
        likeDto.getLikeCount(),
        likeDto.isLiked()
    );
  }
}
