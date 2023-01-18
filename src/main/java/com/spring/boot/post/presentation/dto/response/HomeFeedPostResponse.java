package com.spring.boot.post.presentation.dto.response;

import com.spring.boot.comment.application.dto.CommentResponseDto;
import com.spring.boot.member.application.dto.MemberResponseDto;
import com.spring.boot.post.application.dto.response.PostResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HomeFeedPostResponse {

  private final Long postId;
  private final String title;
  private final int commentCnt;
  private final LocalDateTime createdAt;
  private final String writerName;
  private final String writerProfileImagePath;

  public static HomeFeedPostResponse from(PostResponseDto post) {
    MemberResponseDto writer = post.getWriter();
    List<CommentResponseDto> comments = post.getPostComments();

    return HomeFeedPostResponse.builder()
        .postId(post.getId())
        .title(post.getTitle())
        .commentCnt(comments.size())
        .createdAt(post.getCreatedDate())
        .writerProfileImagePath(writer.getProfileImagePath())
        .writerName(writer.getName())
        .build();
  }

}
