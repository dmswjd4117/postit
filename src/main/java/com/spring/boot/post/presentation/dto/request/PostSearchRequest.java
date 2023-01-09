package com.spring.boot.post.presentation.dto.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostSearchRequest {
  @NotNull
  private List<String> tags;
}
