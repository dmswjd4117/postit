package com.spring.boot.post.presentaion.dto.response;

import com.spring.boot.member.presentaion.dto.response.MemberResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Builder
public class PostInfoResponse {

  private Long id;
  private String title;
  private String content;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private List<PostImageResponse> images;
  private List<PostTagResponse> tags;
  private MemberResponse writer;


  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("title", title)
        .append("body", content)
        .build();
  }
}
