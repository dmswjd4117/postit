package com.spring.boot.post.application.dto;

import com.spring.boot.comment.application.dto.CommentInfoDto;
import com.spring.boot.member.application.dto.MemberInfoDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostInfoDto {
  private Long id;
  private String title;
  private String content;
  private MemberInfoDto writer;
  private List<PostLikeInfoDto> likes;
  private List<CommentInfoDto> comments;
  private List<PostImageInfoDto> images;
  private List<PostTagInfoDto> tags;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
}
