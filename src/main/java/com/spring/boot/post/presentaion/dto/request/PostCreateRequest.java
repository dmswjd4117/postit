package com.spring.boot.post.presentaion.dto.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@AllArgsConstructor
public class PostCreateRequest {

  @NotBlank
  private String title;
  @NotEmpty
  private String content;
  @NotNull
  private List<String> tagNames;

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("title", title)
        .append("body", content)
        .append("post_tags", String.join(",", tagNames))
        .toString();
  }
}
