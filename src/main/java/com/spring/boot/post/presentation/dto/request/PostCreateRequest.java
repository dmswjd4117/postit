package com.spring.boot.post.presentation.dto.request;

import com.spring.boot.post.application.dto.request.PostCreateDto;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostCreateRequest {

  @NotBlank
  private String title;
  @NotEmpty
  private String content;
  @NotNull
  private List<String> tagNames;
  @NotNull
  private boolean isPrivate;

}
