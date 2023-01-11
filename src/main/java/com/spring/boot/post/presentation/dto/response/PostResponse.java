package com.spring.boot.post.presentation.dto.response;


import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PostResponse {

  private Long id;
  private String title;
  private String content;
  private int likeCount;
  private List<PostImageResponse> images;
  private Set<PostTagResponse> tags;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long writerId;
  private String writerName;
  private boolean isPrivate;

  public static PostResponse from(
      Post post) {

    Member writer = post.getWriter();
    Set<PostTagResponse> postTagInfo = PostTagResponse.from(post.getPostTags());
    List<PostImageResponse> postImageInfo = PostImageResponse.from(post.getPostImages());

    return com.spring.boot.post.presentation.dto.response.PostResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .writerId(writer.getId())
        .writerName(writer.getName())
        .likeCount(post.getLikeTotalCount())
        .images(postImageInfo)
        .tags(postTagInfo)
        .createdDate(post.getCreatedDate())
        .modifiedDate(post.getModifiedDate())
        .build();
  }

  public static List<PostResponse> from(List<Post> posts) {
    return posts.stream()
        .map(com.spring.boot.post.presentation.dto.response.PostResponse::from)
        .collect(Collectors.toList());
  }


}
