package com.spring.boot.post.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {

  private Long id;
  private String title;
  private String content;
  private PostWriter postWriter;
  private List<PostImageResponse> images;
  private Set<PostTagResponse> tags;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long likeCount;


  @Getter
  public static class PostImageResponse {

    private Long id;
    private String imagePath;

    public PostImageResponse(Long id, String imagePath) {
      this.id = id;
      this.imagePath = imagePath;
    }
  }

  @Getter
  public static class PostWriter {

    private Long id;
    private String name;

    public PostWriter(Long id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  @Getter
  public static class PostTagResponse {

    private Long id;
    private String tagName;

    public PostTagResponse(Long id, String tagName) {
      this.id = id;
      this.tagName = tagName;
    }
  }
}
