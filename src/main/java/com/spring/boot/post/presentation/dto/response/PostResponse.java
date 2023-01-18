package com.spring.boot.post.presentation.dto.response;

import com.spring.boot.comment.presentation.dto.CommentResponse;
import com.spring.boot.member.presentaion.dto.response.MemberResponse;
import com.spring.boot.post.application.dto.response.PostResponseDto;
import com.spring.boot.tag.presentation.dto.TagResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PostResponse {

  private Long id;
  private String title;
  private String content;
  private int likeCount;
  private List<PostImageResponse> images;
  private Set<TagResponse> tags;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private MemberResponse writer;
  private boolean isPrivate;
  private List<CommentResponse> postComments;

  public static PostResponse from(PostResponseDto postResponseDto) {

    List<PostImageResponse> imageResponses = postResponseDto.getImages()
        .stream()
        .map(dto -> new PostImageResponse(dto.getId(), dto.getImagePath()))
        .collect(Collectors.toList());

    return PostResponse.builder()
        .id(postResponseDto.getId())
        .title(postResponseDto.getTitle())
        .content(postResponseDto.getContent())
        .likeCount(postResponseDto.getLikeCount())
        .images(imageResponses)
        .tags(TagResponse.from(postResponseDto.getTags()))
        .createdDate(postResponseDto.getCreatedDate())
        .modifiedDate(postResponseDto.getModifiedDate())
        .writer(MemberResponse.from(postResponseDto.getWriter()))
        .isPrivate(postResponseDto.isPrivate())
        .postComments(CommentResponse.from(postResponseDto.getPostComments()))
        .build();
  }

  @Getter
  private static class PostImageResponse {

    private Long id;
    private String imageUrlPath;

    public PostImageResponse(Long id, String imageUrlPath) {
      this.id = id;
      this.imageUrlPath = imageUrlPath;
    }

  }

}
