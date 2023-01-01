package com.spring.boot.post.presentaion;

import com.spring.boot.common.response.ApiResult;
import com.spring.boot.post.application.PostQueryService;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.application.dto.PostInfoDto;
import com.spring.boot.post.presentaion.dto.request.PostCreateRequest;
import com.spring.boot.post.presentaion.dto.request.PostUpdateRequest;
import com.spring.boot.post.presentaion.dto.response.PostInfoResponse;
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
  public ApiResult<PostInfoResponse> createPost(
      @AuthenticationPrincipal FormAuthentication authentication,
      @ModelAttribute @Valid PostCreateRequest postCreateRequest,
      @RequestPart(required = false, name = "image") List<MultipartFile> multipartFiles
  ) {

    PostInfoDto post = postService.createPost(
        authentication.id,
        postCreateRequest,
        multipartFiles);

    return ApiResult.success(PostInfoResponse.from(post));
  }

  @DeleteMapping("/{postId}")
  public ApiResult<Long> deletePost(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long postId
  ) {
    return ApiResult.success(postService.deletePost(authentication.id, postId));
  }

  @PutMapping("/{postId}")
  public ApiResult<PostInfoResponse> updatePost(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long postId,
      @ModelAttribute @Valid PostUpdateRequest postUpdateRequest,
      @RequestPart(required = false, name = "image") List<MultipartFile> multipartFiles
  ) {
    PostInfoDto updatedPost = postService.updatePost(
        authentication.id,
        postId,
        postUpdateRequest,
        multipartFiles
    );
    return ApiResult.success(PostInfoResponse.from(updatedPost));
  }

  @GetMapping("/member/{memberId}")
  public ApiResult<List<PostInfoResponse>> getPostByMemberId(
      @PathVariable Long memberId,
      Pageable pageable
  ) {
    return ApiResult.success(
        postQueryService.getPostByMemberId(memberId, pageable)
            .stream()
            .map(PostInfoResponse::from)
            .collect(Collectors.toList())
    );
  }

  @GetMapping("/{postId}")
  public ApiResult<PostInfoResponse> getPostByPostId(
      @PathVariable Long postId
  ) {
    PostInfoDto postInfoDto = postQueryService.getPostByPostId(postId);
    return ApiResult.success(
        PostInfoResponse.from(postInfoDto)
    );
  }

  @GetMapping("/following")
  public ApiResult<List<PostInfoResponse>> findAllFollowingsPost(
      @AuthenticationPrincipal FormAuthentication authentication,
      Pageable pageable
  ) {
    return ApiResult.success(
        postQueryService.getAllFollowingsPost(authentication.id, pageable)
            .stream()
            .map(PostInfoResponse::from)
            .collect(Collectors.toList())
    );
  }
}








