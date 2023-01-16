package com.spring.boot.comment.application.dto;

import com.spring.boot.comment.domain.Comment;
import com.spring.boot.member.domain.Member;
import lombok.Getter;

@Getter
public class CommentDto {
  private final Long commentId;
  private final String body;
  private final Long writerId;
  private final String writerName;
  private final String profileImagePath;

  public CommentDto(Long commentId, String body, Long writerId, String writerName, String profileImagePath) {
    this.commentId = commentId;
    this.body = body;
    this.writerId = writerId;
    this.writerName = writerName;
    this.profileImagePath = profileImagePath;
  }

  public static CommentDto from(Comment comment){
    Member writer = comment.getWriter();
    return new CommentDto(comment.getId(), comment.getBody(), writer.getId(), writer.getName(), writer.getProfileImagePath());
  }

}
