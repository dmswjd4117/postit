package com.spring.boot.post.presentaion.dto;

import com.spring.boot.member.presentaion.dto.MemberResponse;
import com.spring.boot.post.domain.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Builder
public class PostInfoResponse {

  private String title;
  private String body;
  private LocalDateTime createdDate;
  private List<PostImageResponse> images;
  private List<PostTagRequest> postTags;
  private MemberResponse memberResponse;

  public static PostInfoResponse from(Post post) {
    return PostInfoResponse.builder()
        .title(post.getTitle())
        .body(post.getContent())
        .memberResponse(MemberResponse.from(post.getMember()))
        .createdDate(post.getCreatedDate())
        .images(PostImageResponse.from(post.getPostImages()))
        .postTags(PostTagRequest.from(post.getPostTags()))
        .build();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("title", title)
        .append("body", body)
        .build();
  }
}
