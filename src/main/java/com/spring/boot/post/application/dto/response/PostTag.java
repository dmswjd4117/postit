package com.spring.boot.post.application.dto.response;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PostTag {

  private Long id;
  private String name;

  private PostTag(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static Set<PostTag> from(Set<com.spring.boot.post.domain.tag.PostTag> postTags) {
    return postTags
        .stream()
        .map(postTag -> new PostTag(postTag.getId(), postTag.getTagName()))
        .collect(Collectors.toSet());
  }
}