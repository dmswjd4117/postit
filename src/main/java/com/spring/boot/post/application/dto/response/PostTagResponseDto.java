package com.spring.boot.post.application.dto.response;

import com.spring.boot.post.domain.tag.PostTag;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PostTagResponseDto {

  private Long id;
  private String name;

  private PostTagResponseDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static Set<PostTagResponseDto> from(Set<PostTag> postTags) {
    return postTags
        .stream()
        .map(postTag -> new PostTagResponseDto(postTag.getId(), postTag.getTagName()))
        .collect(Collectors.toSet());
  }
}