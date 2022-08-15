package com.spring.boot.comment.application.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentRequestDto {
    private boolean isPrivate;
    private String comment;
}
