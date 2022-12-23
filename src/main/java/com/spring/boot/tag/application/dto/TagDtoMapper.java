package com.spring.boot.tag.application.dto;

import com.spring.boot.post.domain.tag.PostTag;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TagDtoMapper {
  public static TagInfoDto tagInfoDto(PostTag postTag){
    return TagInfoDto.builder()
        .id(postTag.getId())
        .name(postTag.getTagName())
        .build();
  }

  public static List<TagInfoDto> tagInfoDtos(Set<PostTag> postTags) {
    return postTags.stream()
        .map(TagDtoMapper::tagInfoDto)
        .collect(Collectors.toList());
  }
}
