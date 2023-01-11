package com.spring.boot.post.application.dto.request;

import com.spring.boot.post.presentation.dto.request.PostUpdateRequest;
import java.util.List;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class PostUpdateDto {

  private Long writerId;
  private Long postId;
  private String title;
  private String content;
  private List<String> tagNames;
  private List<MultipartFile> multipartFiles;
  private boolean isPrivate;

}
