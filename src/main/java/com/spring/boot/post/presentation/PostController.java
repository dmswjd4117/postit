package com.spring.boot.post.presentation;

import com.spring.boot.common.response.ApiResult;
import com.spring.boot.post.application.PostQueryService;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.application.dto.response.PostResponseDto;
import com.spring.boot.post.presentation.dto.PostAssembler;
import com.spring.boot.post.presentation.dto.request.PostCreateRequest;
import com.spring.boot.post.presentation.dto.request.PostUpdateRequest;
import com.spring.boot.post.presentation.dto.response.PostResponse;
import com.spring.boot.security.FormAuthentication;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/post")
public class PostController {

  private final PostService postService;
  private final PostQueryService postQueryService;

  public PostController(PostService postService, PostQueryService postQueryService) {
    this.postService = postService;
    this.postQueryService = postQueryService;
  }

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ApiResult<PostResponse> createPost(
      @AuthenticationPrincipal FormAuthentication authentication,
      @ModelAttribute @Valid PostCreateRequest postCreateRequest,
      @RequestPart(required = false, name = "image") List<MultipartFile> multipartFiles
  ) {

    PostResponseDto postResponseDto = postService.createPost(
        PostAssembler.toPostCreateRequestDto(authentication.id, postCreateRequest, multipartFiles));

    return ApiResult.success(PostAssembler.toPostInfoResponse(postResponseDto));
  }

  @DeleteMapping("/{postId}")
  public ApiResult<Long> deletePost(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long postId
  ) {
    return ApiResult.success(postService.deletePost(authentication.id, postId));
  }

  @PutMapping("/{postId}")
  public ApiResult<PostResponse> updatePost(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long postId,
      @ModelAttribute @Valid PostUpdateRequest postUpdateRequest,
      @RequestPart(required = false, name = "image") List<MultipartFile> multipartFiles
  ) {
    PostResponseDto updatedPost = postService.updatePost(
        PostAssembler.toPostUpdateRequestDto(authentication.id, postId, postUpdateRequest,
            multipartFiles));
    return ApiResult.success(PostAssembler.toPostInfoResponse(updatedPost));
  }

  @GetMapping("/member/{memberId}")
  public ApiResult<List<PostResponse>> getPostByMemberId(
      @PathVariable Long memberId,
      Pageable pageable
  ) {
    return ApiResult.success(
        postQueryService.getPostByMemberId(memberId, pageable)
            .stream()
            .map(PostAssembler::toPostInfoResponse)
            .collect(Collectors.toList())
    );
  }

  @GetMapping("/{postId}")
  public ApiResult<PostResponse> getPostByPostId(
      @PathVariable Long postId
  ) {
    PostResponseDto postResponseDto = postQueryService.getPostByPostId(postId);
    return ApiResult.success(
        PostAssembler.toPostInfoResponse(postResponseDto)
    );
  }

  @GetMapping("/following")
  public ApiResult<List<PostResponse>> findAllFollowingsPost(
      @AuthenticationPrincipal FormAuthentication authentication,
      Pageable pageable
  ) {
    return ApiResult.success(
        postQueryService.getAllFollowingsPost(authentication.id, pageable)
            .stream()
            .map(PostAssembler::toPostInfoResponse)
            .collect(Collectors.toList())
    );
  }
}








