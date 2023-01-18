package com.spring.boot.post.application.dto.response;

import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class HomeFeedPostDto {

  private final Long postId;
  private final String title;
  private final int commentCnt;
  private final LocalDateTime createdAt;
  private final WriterDto writer;


  public HomeFeedPostDto(Long postId, String title, int commentCnt, LocalDateTime createdAt,
      WriterDto writer) {
    this.postId = postId;
    this.title = title;
    this.commentCnt = commentCnt;
    this.createdAt = createdAt;
    this.writer = writer;
  }

  public static HomeFeedPostDto from(Post post) {
    Member writer = post.getWriter();
    WriterDto writerDto = new WriterDto(writer.getId(), writer.getName(),
        writer.getProfileImagePath());
    return new HomeFeedPostDto(
        post.getId(), post.getTitle(), post.getComments().size(), post.getCreatedDate(), writerDto
    );
  }

  @Getter
  private static class WriterDto {

    private final Long writerId;
    private final String writerName;
    private final String profilePath;

    private WriterDto(Long writerId, String writerName, String profilePath) {
      this.writerId = writerId;
      this.writerName = writerName;
      this.profilePath = profilePath;
    }
  }
}
