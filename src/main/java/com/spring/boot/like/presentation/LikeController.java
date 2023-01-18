package com.spring.boot.like.presentation;

import com.spring.boot.common.response.ApiResult;
import com.spring.boot.like.application.LikeService;
import com.spring.boot.like.presentation.dto.LikeMemberResponse;
import com.spring.boot.like.presentation.dto.LikeResponse;
import com.spring.boot.security.FormAuthentication;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    return ApiResult.success(
        LikeResponse.from(likeService.like(authentication.id, postId))
    );
  }

  @DeleteMapping("/{postId}")
  public ApiResult<LikeResponse> unlike(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long postId) {
    return ApiResult.success(
        LikeResponse.from(likeService.unlike(authentication.id, postId))
    );
  }

  @GetMapping("/list/{postId}")
  public ApiResult<List<LikeMemberResponse>> getLikeMembers(@PathVariable Long postId){
    return ApiResult.success(
        likeService.getLikeMembers(postId).stream()
            .map(LikeMemberResponse::from)
            .collect(Collectors.toList())
    );
  }
}
