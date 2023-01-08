package com.spring.boot.post.presentation.dto;

import com.spring.boot.post.application.dto.request.PostCreateRequestDto;
import com.spring.boot.post.application.dto.response.PostResponseDto;
import com.spring.boot.post.application.dto.request.PostUpdateRequestDto;
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

  public static PostCreateRequestDto toPostCreateRequestDto(Long writerId,
      PostCreateRequest postCreateRequest, List<MultipartFile> multipartFiles) {
    return PostCreateRequestDto.builder()
        .writerId(writerId)
        .content(postCreateRequest.getContent())
        .title(postCreateRequest.getTitle())
        .tagNames(postCreateRequest.getTagNames())
        .multipartFiles(multipartFiles)
        .build();
  }

  public static PostUpdateRequestDto toPostUpdateRequestDto(Long writerId, Long postId,
      PostUpdateRequest postUpdateRequest, List<MultipartFile> multipartFiles) {
    return PostUpdateRequestDto.builder()
        .postId(postId)
        .writerId(writerId)
        .content(postUpdateRequest.getContent())
        .tagNames(postUpdateRequest.getTagNames())
        .title(postUpdateRequest.getTitle())
        .multipartFiles(multipartFiles)
        .build();
  }

  public static PostResponse toPostInfoResponse(PostResponseDto postResponseDto) {

    PostWriter postWriter = new PostWriter(postResponseDto.getWriterId(), postResponseDto.getWriterName());

    List<PostImageResponse> postImageResponses = postResponseDto.getImages()
        .stream()
        .map(dto -> new PostImageResponse(dto.getId(), dto.getImagePath()))
        .collect(Collectors.toList());

    Set<PostTagResponse> postTagResponses = postResponseDto.getTags()
        .stream()
        .map(dto -> new PostTagResponse(dto.getId(), dto.getName()))
        .collect(Collectors.toSet());

    return PostResponse.builder()
        .id(postResponseDto.getId())
        .title(postResponseDto.getTitle())
        .content(postResponseDto.getContent())
        .postWriter(postWriter)
        .images(postImageResponses)
        .tags(postTagResponses)
        .createdDate(postResponseDto.getCreatedDate())
        .modifiedDate(postResponseDto.getModifiedDate())
        .likeCount(postResponseDto.getLikeCount())
        .build();

  }

}
