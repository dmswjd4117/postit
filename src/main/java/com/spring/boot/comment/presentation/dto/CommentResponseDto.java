package com.spring.boot.comment.presentation.dto;

import com.spring.boot.comment.domain.Comment;
import com.spring.boot.member.presentaion.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {
    private String comment;
    private MemberDto commentWriter;
    private Long postId;
    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .comment(comment.getBody())
                .commentWriter(MemberDto.from(comment.getWriter()))
                .postId(comment.getPost().getId())
                .build();
    }
}
