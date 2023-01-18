package com.spring.boot.comment.application.dto;

import com.spring.boot.comment.domain.Comment;
import com.spring.boot.member.domain.Member;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {
  private final Long commentId;
  private final String body;
  private final Long writerId;
  private final String writerName;
  private final String profileImagePath;

  public CommentResponseDto(Long commentId, String body, Long writerId, String writerName, String profileImagePath) {
    this.commentId = commentId;
    this.body = body;
    this.writerId = writerId;
    this.writerName = writerName;
    this.profileImagePath = profileImagePath;
  }

  public static CommentResponseDto from(Comment comment){
    Member writer = comment.getWriter();
    return CommentResponseDto.builder()
        .commentId(comment.getId())
        .body(comment.getBody())
        .writerId(writer.getId())
        .profileImagePath(writer.getProfileImagePath())
        .build();
  }

  public static List<CommentResponseDto> from(List<Comment> comments){
    return comments.stream()
        .map(CommentResponseDto::from)
        .collect(Collectors.toList());
  }
}
