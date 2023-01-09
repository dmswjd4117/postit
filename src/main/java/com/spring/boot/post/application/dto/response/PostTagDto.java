package com.spring.boot.post.application.dto.response;

import com.spring.boot.post.domain.tag.PostTag;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PostTagDto {

  private Long id;
  private String name;

  private PostTagDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static Set<PostTagDto> from(Set<PostTag> postTags) {
    return postTags
        .stream()
        .map(postTag -> new PostTagDto(postTag.getId(), postTag.getTagName()))
        .collect(Collectors.toSet());
  }
}