package com.spring.boot.post.application.dto;

import com.spring.boot.member.application.dto.MemberDtoMapper;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.domain.image.PostImage;
import com.spring.boot.post.domain.like.PostLike;
import com.spring.boot.post.domain.tag.PostTag;
import java.util.List;
import java.util.Set;
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
            .writer(MemberDtoMapper.memberInfoDto(post.getWriter()))

            .likes(likeInfoDtos(post.getPostLikes()))
            .images(postImageInfoDtos(post.getPostImages()))
            .tags(postTagInfoDtos(post.getPostTags()))

            .createdDate(post.getCreatedDate())
            .modifiedDate(post.getModifiedDate())

            .build())
        .collect(Collectors.toList());
  }

  public static PostLikeInfoDto likeInfoDto(PostLike like){
    return PostLikeInfoDto.builder()
        .id(like.getId())
        .memberInfoDto(MemberDtoMapper.memberInfoDto(like.getMember()))
        .build();
  }

  public static List<PostLikeInfoDto> likeInfoDtos(List<PostLike> likes) {
    return likes.stream()
        .map(PostDtoMapper::likeInfoDto)
        .collect(Collectors.toList());
  }

  public static PostTagInfoDto postTagInfoDto(PostTag postTag){
    return PostTagInfoDto.builder()
        .id(postTag.getId())
        .name(postTag.getTagName())
        .build();
  }

  public static List<PostTagInfoDto> postTagInfoDtos(Set<PostTag> postTags) {
    return postTags.stream()
        .map(PostDtoMapper::postTagInfoDto)
        .collect(Collectors.toList());
  }

  public static PostImageInfoDto postImageInfoDto(PostImage postImage){
    return PostImageInfoDto.builder()
        .id(postImage.getId())
        .imagePath(postImage.getImagePath())
        .build();
  }

  public static List<PostImageInfoDto> postImageInfoDtos(List<PostImage> postImages) {
    return postImages.stream()
        .map(PostDtoMapper::postImageInfoDto)
        .collect(Collectors.toList());
  }
}
