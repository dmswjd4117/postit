package com.spring.boot.post.presentaion.dto.request;

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
public class PostTagRequest {

  private Long id;
  private String tagName;

  public static PostTagRequest from(PostTag postTag) {
    return new PostTagRequest(postTag.getId(), postTag.getTagName());
  }

  public static List<PostTagRequest> from(Set<PostTag> postTags) {
    return postTags.stream()
        .map(PostTagRequest::from)
        .collect(Collectors.toList());
  }

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
