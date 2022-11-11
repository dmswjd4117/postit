package com.spring.boot.comment.presentation.dto;

import com.spring.boot.comment.domain.Comment;
import com.spring.boot.member.presentaion.dto.MemberResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {
    private String comment;
    private MemberResponse commentWriter;
    private Long postId;
    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .comment(comment.getBody())
                .commentWriter(MemberResponse.from(comment.getWriter()))
                .postId(comment.getPost().getId())
                .build();
    }
}
