package com.spring.boot.post.presentation;

import static com.spring.boot.post.presentation.dto.PostAssembler.toPostCreateDto;
import static com.spring.boot.post.presentation.dto.PostAssembler.toPostUpdateDto;

import com.spring.boot.common.response.ApiResult;
import com.spring.boot.post.application.PostQueryService;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.presentation.dto.request.PostCreateRequest;
import com.spring.boot.post.presentation.dto.request.PostUpdateRequest;
import com.spring.boot.post.application.dto.response.PostInfoDto;
import com.spring.boot.security.FormAuthentication;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
  public ApiResult<PostInfoDto> createPost(
      @AuthenticationPrincipal FormAuthentication authentication,
      @ModelAttribute @Valid PostCreateRequest postCreateRequest,
      @RequestPart(required = false, name = "image") List<MultipartFile> multipartFiles
  ) {
    PostInfoDto postInfoDto = postService.createPost(
        toPostCreateDto(authentication.id, postCreateRequest, multipartFiles));

    return ApiResult.success(postInfoDto);
  }

  @DeleteMapping("/{postId}")
  public ApiResult<Long> deletePost(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long postId
  ) {
    return ApiResult.success(postService.deletePost(authentication.id, postId));
  }

  @PutMapping("/{postId}")
  public ApiResult<PostInfoDto> updatePost(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long postId,
      @ModelAttribute @Valid PostUpdateRequest postUpdateRequest,
      @RequestPart(required = false, name = "image") List<MultipartFile> multipartFiles
  ) {
    PostInfoDto updatedPost = postService.updatePost(
        toPostUpdateDto(authentication.id, postUpdateRequest, multipartFiles, postId));

    return ApiResult.success(updatedPost);
  }

}








