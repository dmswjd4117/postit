package com.spring.boot.post.presentation;

import com.spring.boot.common.response.ApiResult;
import com.spring.boot.post.application.PostQueryService;
import com.spring.boot.post.presentation.dto.response.HomeFeedPostResponse;
import com.spring.boot.post.presentation.dto.request.PostSearchRequest;
import com.spring.boot.post.presentation.dto.response.PostResponse;
import com.spring.boot.security.FormAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

  @GetMapping("/home")
  public ApiResult<List<HomeFeedPostResponse>> getHomeFeedPost(
      @AuthenticationPrincipal FormAuthentication authentication) {
    return ApiResult.success(
        postQueryService.getHomeFeedPost(authentication.id, 10, null)
            .stream().map(HomeFeedPostResponse::from)
            .collect(Collectors.toList())
    );
  }

  @GetMapping("/member/{memberId}")
  public ApiResult<List<PostResponse>> getPostByMemberId(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long memberId,
      @PageableDefault Pageable pageable
  ) {
    Long readerId = getReaderId(authentication);
    return ApiResult.success(
        postQueryService.getPostByWriterId(memberId, pageable, readerId)
            .stream()
            .map(PostResponse::from)
            .collect(Collectors.toList())
    );
  }

  @GetMapping("/{postId}")
  public ApiResult<PostResponse> getPostByPostId(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long postId
  ) {
    Long readerId = getReaderId(authentication);
    PostResponse postResponse = PostResponse.from(postQueryService.getPostByPostId(postId, readerId));
    return ApiResult.success(postResponse);
  }

  private Long getReaderId(FormAuthentication authentication) {
    if (authentication == null) {
      return null;
    }
    return authentication.id;
  }

  @GetMapping("/following")
  public ApiResult<List<PostResponse>> getFollowingsPost(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PageableDefault Pageable pageable
  ) {
    return ApiResult.success(
        postQueryService.getFollowingsPost(authentication.id, pageable)
            .stream()
            .map(PostResponse::from)
            .collect(Collectors.toList())
    );
  }

  @GetMapping("/tag")
  public ApiResult<List<PostResponse>> getPostByTags(
      @AuthenticationPrincipal FormAuthentication authentication,
      @RequestBody @Valid PostSearchRequest postSearchRequest,
      @PageableDefault Pageable pageable) {
    Long readerId = getReaderId(authentication);
    return ApiResult.success(
            postQueryService.getPostByTags(postSearchRequest.getTags(), pageable, readerId)
                .stream()
                .map(PostResponse::from)
                .collect(Collectors.toList())
    );
  }
}
