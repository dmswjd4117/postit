package com.spring.boot.comment.presentation.dto;

import com.spring.boot.comment.application.dto.CommentResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class CommentResponse {

  private final Long id;
  private final String body;
  private final String writerName;
  private final Long writerId;
  private final String writerProfileImageUrl;

  public CommentResponse(Long id, String body, String writerName, Long writerId,
      String writerProfileImageUrl) {
    this.id = id;
    this.body = body;
    this.writerName = writerName;
    this.writerId = writerId;
    this.writerProfileImageUrl = writerProfileImageUrl;
  }

  public static CommentResponse from(CommentResponseDto commentResponseDto) {
    return new CommentResponse(commentResponseDto.getCommentId(), commentResponseDto.getBody(),
        commentResponseDto.getWriterName(), commentResponseDto.getWriterId(),
        commentResponseDto.getProfileImagePath());
  }

  public static List<CommentResponse> from(List<CommentResponseDto> comments) {
    return comments.stream()
        .map(CommentResponse::from)
        .collect(Collectors.toList());
  }
}