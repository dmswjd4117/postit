package com.spring.boot.post.presentation;

import com.spring.boot.common.response.ApiResult;
import com.spring.boot.post.application.PostQueryService;
import com.spring.boot.post.presentation.dto.request.PostSearchRequest;
import com.spring.boot.post.presentation.dto.response.PostResponse;
import com.spring.boot.security.FormAuthentication;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/post")
public class PostQueryController {

  private final PostQueryService postQueryService;

  public PostQueryController(PostQueryService postQueryService) {
    this.postQueryService = postQueryService;
  }


  @GetMapping("/member/{memberId}")
  public ApiResult<List<PostResponse>> getPostByMemberId(
      @PathVariable Long memberId,
      @AuthenticationPrincipal FormAuthentication authentication,
      Pageable pageable
  ) {
    return ApiResult.success(
        new ArrayList<>(postQueryService.getPostByWriterId(memberId,authentication.id, pageable))
    );
  }

  @GetMapping("/{postId}")
  public ApiResult<PostResponse> getPostByPostId(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long postId
  ) {
    PostResponse postResponse = postQueryService.getPostByPostId(postId, authentication.id);
    return ApiResult.success(postResponse);
  }

  @GetMapping("/following")
  public ApiResult<List<PostResponse>> getFollowingsPost(
      @AuthenticationPrincipal FormAuthentication authentication,
      Pageable pageable
  ) {
    return ApiResult.success(
        new ArrayList<>(postQueryService.getFollowingsPost(authentication.id, pageable))
    );
  }

  @GetMapping("/tag")
  public ApiResult<List<PostResponse>> getPostByTags(
      @RequestBody @Valid PostSearchRequest postSearchRequest,
      Pageable pageable) {
    return ApiResult.success(
        new ArrayList<>(postQueryService.getPostByTags(postSearchRequest.getTags(), pageable))
    );
  }
}
