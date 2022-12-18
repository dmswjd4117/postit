package com.spring.boot.post.presentaion.dto;

import java.util.List;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.assertj.core.util.Strings;

@Getter
public class PostUpdateRequest {
  private String title;
  private String content;
  private List<String> tagNames;

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("title", title)
        .append("content", content)
        .append("tag names", String.join(",", tagNames))
        .toString();
  }
}
