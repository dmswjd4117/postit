package com.spring.boot.post.application.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class PostCreateDto {

  private Long writerId;
  private String title;
  private String content;
  private List<String> tagNames;
  private List<MultipartFile> multipartFiles;
  private boolean isPrivate;

}
