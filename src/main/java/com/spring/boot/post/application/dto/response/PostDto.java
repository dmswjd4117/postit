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
import lombok.ToString;

@ToString
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PostDto {

  private Long id;
  private String title;
  private String content;
  private Long likeCount;
  private List<PostImageDto> images;
  private Set<PostTagDto> tags;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long writerId;
  private String writerName;

  public static PostDto from(
      Post post) {

    Member writer = post.getWriter();
    Set<PostTagDto> postTagInfo = PostTagDto.from(post.getPostTags());
    List<PostImageDto> postImageInfo = PostImageDto.from(post.getPostImages());

    return PostDto.builder()
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

  public static List<PostDto> from(List<Post> posts) {
    return posts.stream()
        .map(PostDto::from)
        .collect(Collectors.toList());
  }


}
