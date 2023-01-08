package com.spring.boot.post.application.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class PostUpdateRequestDto {

  private Long writerId;
  private Long postId;
  private String title;
  private String content;
  private List<String> tagNames;
  private List<MultipartFile> multipartFiles;
}
