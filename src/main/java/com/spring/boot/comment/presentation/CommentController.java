package com.spring.boot.comment.presentation;

import com.spring.boot.comment.application.CommentService;
import com.spring.boot.comment.presentation.dto.CommentRequestDto;
import com.spring.boot.comment.presentation.dto.CommentResponseDto;
import com.spring.boot.common.dto.ApiResult;
import com.spring.boot.security.FormAuthentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
            @RequestBody CommentRequestDto commentRequestDto){
        return ApiResult.success(
                CommentResponseDto.from(commentService.createComment(
                        formAuthentication.id,
                        commentRequestDto.getComment(),
                        commentRequestDto.getPostId()))
        );
    }

}
