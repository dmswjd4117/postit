package com.spring.boot.post.application.dto.response;


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

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PostResponseDto {

  private Long id;
  private String title;
  private String content;
  private Long likeCount;
  private List<PostImageResponseDto> images;
  private Set<PostTagResponseDto> tags;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long writerId;
  private String writerName;

  public static PostResponseDto from(
      Post post) {

    Member writer = post.getWriter();
    Set<PostTagResponseDto> postTagInfo = PostTagResponseDto.from(post.getPostTags());
    List<PostImageResponseDto> postImageInfo = PostImageResponseDto.from(post.getPostImages());

    return PostResponseDto.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .writerId(writer.getId())
        .writerName(writer.getName())
        .likeCount((long) post.getLikes().size())
        .images(postImageInfo)
        .tags(postTagInfo)
        .createdDate(post.getCreatedDate())
        .modifiedDate(post.getModifiedDate())
        .build();
  }

  public static List<PostResponseDto> from(List<Post> posts) {
    return posts.stream()
        .map(PostResponseDto::from)
        .collect(Collectors.toList());
  }


}
