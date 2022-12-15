package com.spring.boot.post.application.dto;

import com.spring.boot.post.domain.tag.PostTag;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Setter
@Getter
public class PostTagDto {

  private Long id;
  private String tagName;

  public static PostTagDto from(PostTag postTag) {
    return new PostTagDto(postTag.getId(), postTag.getTagName());
  }

  public static List<PostTagDto> from(Set<PostTag> postTags) {
    return postTags.stream()
        .map(PostTagDto::from)
        .collect(Collectors.toList());
  }

  private PostTagDto(Long id, String tagName) {
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
