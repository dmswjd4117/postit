package com.spring.boot.post.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Setter
@Getter
public class PostTagRequest {

  private Long id;
  private String tagName;

  private PostTagRequest(Long id, String tagName) {
    this.id = id;
    this.tagName = tagName;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("tagName", tagName)
        .toString();
  }
}
