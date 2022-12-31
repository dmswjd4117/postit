package com.spring.boot.comment.presentation;

import com.spring.boot.comment.application.CommentService;
import com.spring.boot.comment.presentation.dto.CommentRequest;
import com.spring.boot.comment.presentation.dto.CommentResponse;
import com.spring.boot.common.response.ApiResult;
import com.spring.boot.security.FormAuthentication;
import javax.validation.Valid;
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
    public ApiResult<CommentResponse> createComment(
            @AuthenticationPrincipal FormAuthentication formAuthentication,
            @RequestBody @Valid CommentRequest commentRequest){
        return ApiResult.success(
                CommentResponse.from(commentService.createComment(
                        formAuthentication.id,
                        commentRequest.getComment(),
                        commentRequest.getPostId()))
        );
    }

}
