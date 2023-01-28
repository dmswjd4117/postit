package com.spring.boot.comment.presentation;

import com.spring.boot.comment.application.CommentService;
import com.spring.boot.comment.application.dto.CommentResponseDto;
import com.spring.boot.comment.presentation.dto.CommentRequest;
import com.spring.boot.common.presentation.response.ApiResult;
import com.spring.boot.security.FormAuthentication;
import javax.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @PostMapping
  public ApiResult<CommentResponseDto> createComment(
      @AuthenticationPrincipal FormAuthentication formAuthentication,
      @RequestBody @Valid CommentRequest commentRequest) {
    return ApiResult.success(
        commentService.createComment(
            formAuthentication.id,
            commentRequest.getComment(),
            commentRequest.getPostId())
    );
  }

  @DeleteMapping("/{commentId}")
  public ApiResult<Long> deleteComment(
      @AuthenticationPrincipal FormAuthentication formAuthentication,
      @RequestParam Long commentId
  ) {
    return ApiResult.success(
        commentService.deleteComment(
            formAuthentication.id,
            commentId
        )
    );
  }

}
