package com.spring.boot.post.application.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class PostCreateRequestDto {

  private Long writerId;
  private String title;
  private String content;
  private List<String> tagNames;
  private List<MultipartFile> multipartFiles;
}
