package com.spring.boot.post.presentation.dto;

import com.spring.boot.post.application.dto.request.PostCreateDto;
import com.spring.boot.post.application.dto.request.PostUpdateDto;
import com.spring.boot.post.application.dto.response.PostDto;
import com.spring.boot.post.presentation.dto.request.PostCreateRequest;
import com.spring.boot.post.presentation.dto.request.PostUpdateRequest;
import com.spring.boot.post.presentation.dto.response.PostResponse;
import com.spring.boot.post.presentation.dto.response.PostResponse.PostImageResponse;
import com.spring.boot.post.presentation.dto.response.PostResponse.PostTagResponse;
import com.spring.boot.post.presentation.dto.response.PostResponse.PostWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostAssembler {

  public static PostCreateDto toPostCreateRequestDto(Long writerId,
      PostCreateRequest postCreateRequest, List<MultipartFile> multipartFiles) {
    return PostCreateDto.builder()
        .writerId(writerId)
        .content(postCreateRequest.getContent())
        .title(postCreateRequest.getTitle())
        .tagNames(postCreateRequest.getTagNames())
        .multipartFiles(multipartFiles)
        .build();
  }

  public static PostUpdateDto toPostUpdateRequestDto(Long writerId, Long postId,
      PostUpdateRequest postUpdateRequest, List<MultipartFile> multipartFiles) {
    return PostUpdateDto.builder()
        .postId(postId)
        .writerId(writerId)
        .content(postUpdateRequest.getContent())
        .tagNames(postUpdateRequest.getTagNames())
        .title(postUpdateRequest.getTitle())
        .multipartFiles(multipartFiles)
        .build();
  }

  public static PostResponse toPostInfoResponse(PostDto postDto) {

    PostWriter postWriter = new PostWriter(postDto.getWriterId(),
        postDto.getWriterName());

    List<PostImageResponse> postImageResponses = postDto.getImages()
        .stream()
        .map(dto -> new PostImageResponse(dto.getId(), dto.getImagePath()))
        .collect(Collectors.toList());

    Set<PostTagResponse> postTagResponses = postDto.getTags()
        .stream()
        .map(dto -> new PostTagResponse(dto.getId(), dto.getName()))
        .collect(Collectors.toSet());

    return PostResponse.builder()
        .id(postDto.getId())
        .title(postDto.getTitle())
        .content(postDto.getContent())
        .postWriter(postWriter)
        .images(postImageResponses)
        .tags(postTagResponses)
        .createdDate(postDto.getCreatedDate())
        .modifiedDate(postDto.getModifiedDate())
        .likeCount(postDto.getLikeCount())
        .build();

  }
}
