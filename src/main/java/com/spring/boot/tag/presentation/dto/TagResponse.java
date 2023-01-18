package com.spring.boot.tag.presentation.dto;

import com.spring.boot.tag.application.dto.TagResponseDto;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;


@Getter
public class TagResponse {

  private Long id;
  private String tagName;

  public TagResponse(Long id, String tagName) {
    this.id = id;
    this.tagName = tagName;
  }

  public static TagResponse from(TagResponseDto tag){
    return new TagResponse(tag.getId(), tag.getName());
  }

  public static Set<TagResponse> from(Set<TagResponseDto> tags) {
    return tags.stream()
        .map(TagResponse::from)
        .collect(Collectors.toSet());
  }
}
