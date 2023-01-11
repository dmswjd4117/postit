package com.spring.boot.post.presentation.dto.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
public class PostUpdateRequest {
  @NotBlank
  private String title;
  @NotEmpty
  private String content;
  @NotNull
  private List<String> tagNames;
  @NotNull
  private boolean isPrivate;
  public PostUpdateRequest(String title, String content, List<String> tagNames, boolean isPrivate) {
    this.title = title;
    this.content = content;
    this.tagNames = tagNames;
    this.isPrivate = isPrivate;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("title", title)
        .append("content", content)
        .append("tag names", String.join(",", tagNames))
        .toString();
  }
}
