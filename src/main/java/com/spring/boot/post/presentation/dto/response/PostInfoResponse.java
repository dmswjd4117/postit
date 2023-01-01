package com.spring.boot.post.presentation.dto.response;

import com.spring.boot.post.application.dto.PostInfoDto;
import com.spring.boot.post.application.dto.PostInfoDto.PostImageInfoDto;
import com.spring.boot.post.application.dto.PostInfoDto.PostTagInfoDto;
import com.spring.boot.post.application.dto.PostInfoDto.PostWriterDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PostInfoResponse {

  private Long id;
  private String title;
  private String content;
  private PostWriter postWriter;
  private List<PostImageResponse> images;
  private Set<PostTagResponse> tags;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;

  public static PostInfoResponse from(PostInfoDto postInfoDto) {
    PostWriter postWriter = PostWriter.from(postInfoDto.getWriter());
    List<PostImageResponse> postImageResponses = PostImageResponse.from(postInfoDto.getImages());
    Set<PostTagResponse> postTagResponses = PostTagResponse.from(postInfoDto.getTags());

    return PostInfoResponse.builder()
        .id(postInfoDto.getId())
        .title(postInfoDto.getTitle())
        .content(postInfoDto.getContent())
        .postWriter(postWriter)
        .images(postImageResponses)
        .tags(postTagResponses)
        .createdDate(postInfoDto.getCreatedDate())
        .modifiedDate(postInfoDto.getModifiedDate())
        .build();
  }


  @Getter
  private static class PostImageResponse {

    private Long id;
    private String imagePath;

    private PostImageResponse(Long id, String imagePath) {
      this.id = id;
      this.imagePath = imagePath;
    }

    public static List<PostImageResponse> from(List<PostImageInfoDto> images) {
      return images
          .stream()
          .map(dto -> new PostImageResponse(dto.getId(), dto.getImagePath()))
          .collect(Collectors.toList());
    }
  }

  @Getter
  private static class PostWriter {

    private Long id;
    private String name;

    private PostWriter(Long id, String name) {
      this.id = id;
      this.name = name;
    }

    public static PostWriter from(PostWriterDto writer) {
      return new PostWriter(writer.getId(), writer.getName());
    }
  }

  @Getter
  public static class PostTagResponse {

    private Long id;
    private String tagName;

    private PostTagResponse(Long id, String tagName) {
      this.id = id;
      this.tagName = tagName;
    }

    public static Set<PostTagResponse> from(Set<PostTagInfoDto> tags) {
      return tags
          .stream()
          .map(dto -> new PostTagResponse(dto.getId(), dto.getName()))
          .collect(Collectors.toSet());
    }
  }
}
