package com.spring.boot.like.presentation.dto;

import com.spring.boot.like.application.dto.LikeResponseDto;

public class LikeResponse {
  private int likeTotalCount;
  private boolean liked;

  public LikeResponse(int likeTotalCount, boolean liked) {
    this.likeTotalCount = likeTotalCount;
    this.liked = liked;
  }

  public static LikeResponse from(LikeResponseDto likeResponseDto) {
    return new LikeResponse(
        likeResponseDto.getLikeCount(),
        likeResponseDto.isLiked()
    );
  }
}
