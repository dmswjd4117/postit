package com.spring.boot.post.application.dto;

import com.spring.boot.image.application.dto.ImageDtoMapper;
import com.spring.boot.post.domain.Post;
import com.spring.boot.tag.application.dto.TagDtoMapper;
import com.spring.boot.tag.application.dto.TagInfoDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostDtoMapper {
  public static List<PostInfoDto> postInfoDtos(List<Post> posts){
    return posts.stream()
        .map(post -> PostInfoDto.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .writer(post.getWriter())
            .likeCount(post.getLikes().size())
            .images(ImageDtoMapper.imageInfoDtos(post.getImages()))
            .tags(TagDtoMapper.tagInfoDtos(post.getPostTags()))
            .build())
        .collect(Collectors.toList());
  }
}
