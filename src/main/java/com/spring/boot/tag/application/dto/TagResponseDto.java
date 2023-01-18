package com.spring.boot.tag.application.dto;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TagResponseDto {

  private Long id;
  private String name;

  private TagResponseDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static Set<TagResponseDto> from(Set<com.spring.boot.post.domain.tag.PostTag> postTags) {
    return postTags
        .stream()
        .map(postTag -> new TagResponseDto(postTag.getId(), postTag.getTagName()))
        .collect(Collectors.toSet());
  }
}