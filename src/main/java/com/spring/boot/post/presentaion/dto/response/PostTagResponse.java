package com.spring.boot.post.presentaion.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostTagResponse {
  private Long id;
  private String tagName;
}
