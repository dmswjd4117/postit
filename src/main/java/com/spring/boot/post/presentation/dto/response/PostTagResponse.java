package com.spring.boot.post.presentation.dto.response;

import com.spring.boot.post.domain.tag.PostTag;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PostTagResponse {

  private Long id;
  private String name;

  private PostTagResponse(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static Set<PostTagResponse> from(Set<PostTag> postTags) {
    return postTags
        .stream()
        .map(postTag -> new PostTagResponse(postTag.getId(), postTag.getTagName()))
        .collect(Collectors.toSet());
  }
}