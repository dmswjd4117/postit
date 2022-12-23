package com.spring.boot.post.presentaion.dto;


import com.spring.boot.post.application.dto.PostImageInfoDto;
import com.spring.boot.member.presentaion.dto.request.MemberMapper;
import com.spring.boot.post.application.dto.PostInfoDto;
import com.spring.boot.post.presentaion.dto.response.PostImageResponse;
import com.spring.boot.post.presentaion.dto.response.PostInfoResponse;
import com.spring.boot.post.presentaion.dto.response.PostTagResponse;
import com.spring.boot.post.application.dto.PostTagInfoDto;
import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {

  public static PostInfoResponse postInfoResponse(PostInfoDto postInfoDto){
    return PostInfoResponse.builder()
        .id(postInfoDto.getId())
        .title(postInfoDto.getTitle())
        .content(postInfoDto.getContent())
        .writer(MemberMapper.memberResponse(postInfoDto.getWriter()))
        .images(postImageResponses(postInfoDto.getImages()))
        .tags(postTagResponse(postInfoDto.getTags()))
        .createdDate(postInfoDto.getCreatedDate())
        .modifiedDate(postInfoDto.getModifiedDate())
        .build();
  }

  private static List<PostTagResponse> postTagResponse(List<PostTagInfoDto> tags) {
    return tags.stream()
        .map(tag -> PostTagResponse.builder()
            .id(tag.getId())
            .tagName(tag.getName())
            .build()
        )
        .collect(Collectors.toList());
  }


  private static List<PostImageResponse> postImageResponses(List<PostImageInfoDto> postImageInfoDtos) {
    return postImageInfoDtos.stream()
        .map(postImageInfoDto -> new PostImageResponse(postImageInfoDto.getId(),
            postImageInfoDto.getImagePath()))
        .collect(Collectors.toList());
  }

}
