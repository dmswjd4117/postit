package com.spring.boot.post.application.dto;

import com.spring.boot.member.presentaion.dto.MemberResponse;
import com.spring.boot.post.domain.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostInfoResponse {

  private String title;
  private String body;
  private LocalDateTime createdDate;
  private List<PostImageResponse> images;
  private List<PostTagDto> postTags;
  private MemberResponse memberResponse;

  public static PostInfoResponse from(Post post) {
    return PostInfoResponse.builder()
        .title(post.getTitle())
        .body(post.getBody())
        .memberResponse(MemberResponse.from(post.getMember()))
        .createdDate(post.getCreatedDate())
        .images(PostImageResponse.from(post.getImages()))
        .postTags(PostTagDto.from(post.getPostTags()))
        .build();
  }
}
