package com.spring.boot.post.presentation.dto;

import com.spring.boot.post.application.dto.request.PostCreateDto;
import com.spring.boot.post.application.dto.request.PostUpdateDto;
import com.spring.boot.post.presentation.dto.request.PostCreateRequest;
import com.spring.boot.post.presentation.dto.request.PostUpdateRequest;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class PostAssembler {
  public static PostUpdateDto toPostUpdateDto(Long writerId, PostUpdateRequest postUpdateRequest,
      List<MultipartFile> multipartFiles, Long postId) {
    return PostUpdateDto.builder()
        .writerId(writerId)
        .postId(postId)
        .content(postUpdateRequest.getContent())
        .title(postUpdateRequest.getTitle())
        .tagNames(postUpdateRequest.getTagNames())
        .multipartFiles(multipartFiles)
        .isPrivate(postUpdateRequest.isPrivate())
        .build();
  }

  public static PostCreateDto toPostCreateDto(Long writerId, PostCreateRequest postCreateRequest,
      List<MultipartFile> multipartFiles) {
    return PostCreateDto.builder()
        .writerId(writerId)
        .content(postCreateRequest.getContent())
        .title(postCreateRequest.getTitle())
        .tagNames(postCreateRequest.getTagNames())
        .multipartFiles(multipartFiles)
        .isPrivate(postCreateRequest.isPrivate())
        .build();
  }
}
