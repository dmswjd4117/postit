package com.spring.boot.post.presentation;

import com.spring.boot.common.response.ApiResult;
import com.spring.boot.post.application.PostQueryService;
import com.spring.boot.post.application.dto.response.PostInfo;
import com.spring.boot.post.presentation.dto.request.PostSearchRequest;
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
  public ApiResult<List<PostInfo>> getPostByMemberId(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long memberId,
      Pageable pageable
  ) {
    Long readerId = getReaderId(authentication);
    return ApiResult.success(
        new ArrayList<>(postQueryService.getPostByWriterId(memberId, pageable, readerId))
    );
  }

  @GetMapping("/{postId}")
  public ApiResult<PostInfo> getPostByPostId(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long postId
  ) {
    Long readerId = getReaderId(authentication);
    PostInfo postInfo = postQueryService.getPostByPostId(postId, readerId);
    return ApiResult.success(postInfo);
  }

  private Long getReaderId(FormAuthentication authentication) {
    if (authentication == null) {
      return null;
    }
    return authentication.id;
  }

  @GetMapping("/following")
  public ApiResult<List<PostInfo>> getFollowingsPost(
      @AuthenticationPrincipal FormAuthentication authentication,
      Pageable pageable
  ) {
    return ApiResult.success(
        new ArrayList<>(postQueryService.getFollowingsPost(authentication.id, pageable))
    );
  }

  @GetMapping("/tag")
  public ApiResult<List<PostInfo>> getPostByTags(
      @AuthenticationPrincipal FormAuthentication authentication,
      @RequestBody @Valid PostSearchRequest postSearchRequest,
      Pageable pageable) {
    Long readerId = getReaderId(authentication);
    return ApiResult.success(
        new ArrayList<>(
            postQueryService.getPostByTags(postSearchRequest.getTags(), pageable, readerId))
    );
  }
}
