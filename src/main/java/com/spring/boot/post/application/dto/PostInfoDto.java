package com.spring.boot.post.application.dto;

import com.spring.boot.comment.application.dto.CommentInfoDto;
import com.spring.boot.comment.domain.Comment;
import com.spring.boot.image.application.dto.ImageInfoDto;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.image.Image;
import com.spring.boot.tag.application.dto.TagInfoDto;
import com.spring.boot.tag.domain.Tag;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostInfoDto {
  private Long id;
  private String title;
  private String content;
  private Member writer;
  private int likeCount;
  private List<CommentInfoDto> comments;
  private List<ImageInfoDto> images;
  private List<TagInfoDto> tags;
}
