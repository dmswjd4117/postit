package com.spring.boot.post.presentaion;

import com.spring.boot.common.response.ApiResult;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.presentaion.dto.PostInfoResponse;
import com.spring.boot.post.presentaion.dto.PostCreateRequest;
import com.spring.boot.post.presentaion.dto.PostUpdateRequest;
import com.spring.boot.security.FormAuthentication;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/post")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ApiResult<PostInfoResponse> createPost(
      @AuthenticationPrincipal FormAuthentication authentication,
      @ModelAttribute @Valid PostCreateRequest postCreateRequest,
      @RequestPart(required = false, name = "image") List<MultipartFile> multipartFiles
  ) {

    Post post = postService.createPost(
        authentication.id,
        postCreateRequest,
        multipartFiles);

    return ApiResult.success(PostInfoResponse.from(post));
  }

  @DeleteMapping("/{postId}")
  public ApiResult<Long> deletePost(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long postId
  ){
    return ApiResult.success(postService.deletePost(authentication.id, postId));
  }

  @PutMapping("/{postId}")
  public ApiResult<PostInfoResponse> updatePost(
      @AuthenticationPrincipal FormAuthentication authentication,
      @PathVariable Long postId,
      @ModelAttribute @Valid PostUpdateRequest postUpdateRequest,
      @RequestPart(required = false, name = "image") List<MultipartFile> multipartFiles
  ){
    Post updatedPost = postService.updatePost(
        authentication.id,
        postId,
        postUpdateRequest,
        multipartFiles
    );
    return ApiResult.success(PostInfoResponse.from(updatedPost));
  }

  @GetMapping("/member/{memberId}")
  public ApiResult<List<PostInfoResponse>> getPostByMemberId(
      @PathVariable Long memberId
  ) {
    return ApiResult.success(
        postService.getPostByMemberId(memberId)
            .stream()
            .map(PostInfoResponse::from)
            .collect(Collectors.toList())
    );
  }

  @GetMapping("/{postId}")
  public ApiResult<PostInfoResponse> getPostByPostId(
      @PathVariable Long postId
  ) {
    return ApiResult.success(
        PostInfoResponse.from(postService.getPostByPostId(postId))
    );
  }

//  @GetMapping("/all")
//  public ApiResult<List<PostInfoResponse>> getAllFollowingsPost(
//      @AuthenticationPrincipal FormAuthentication authentication,
//      Pageable pageable
//  ){
//    return ApiResult.success(
//        postService.getAllFollowingsPost(authentication.id, pageable)
//            .stream()
//            .map(PostInfoResponse::from)
//            .collect(Collectors.toList())
//    );
//  }
}








