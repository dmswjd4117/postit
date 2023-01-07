package com.spring.boot.like.presentation;

import com.spring.boot.common.response.ApiResult;
import com.spring.boot.like.application.LikeService;
import com.spring.boot.like.application.dto.LikeResponseDto;
import com.spring.boot.like.presentation.dto.LikeResponse;
import com.spring.boot.security.FormAuthentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like/post")
public class LikeController {

  private final LikeService likeService;

  public LikeController(LikeService likeService) {
    this.likeService = likeService;
  }

  @PostMapping("/{postId}")
  public ApiResult<LikeResponse> like(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long postId) {
    LikeResponseDto likeResponseDto = likeService.like(authentication.id, postId);
    return ApiResult.success(LikeResponse.from(likeService.unlike(authentication.id, postId)));
  }

  @DeleteMapping("/{postId}")
  public ApiResult<LikeResponse> unlike(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long postId) {
    LikeResponseDto likeResponseDto = likeService.unlike(authentication.id, postId);
    return ApiResult.success(LikeResponse.from(likeService.unlike(authentication.id, postId)));
  }
}
