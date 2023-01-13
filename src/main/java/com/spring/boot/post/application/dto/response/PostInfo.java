package com.spring.boot.post.application.dto.response;


import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PostInfo {

  private Long id;
  private String title;
  private String content;
  private int likeCount;
  private List<PostImage> images;
  private Set<PostTag> tags;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long writerId;
  private String writerName;
  private boolean isPrivate;

  public PostInfo(Long id, String title, String content, int likeCount,
      List<PostImage> images, Set<PostTag> tags, LocalDateTime createdDate,
      LocalDateTime modifiedDate, Long writerId, String writerName, boolean isPrivate) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.likeCount = likeCount;
    this.images = images;
    this.tags = tags;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
    this.writerId = writerId;
    this.writerName = writerName;
    this.isPrivate = isPrivate;
  }

  public static PostInfo from(
      Post post) {

    Member writer = post.getWriter();
    Set<PostTag> postTags = PostTag.from(post.getPostTags());
    List<PostImage> postImages = PostImage.from(post.getPostImages());

    return new PostInfo(post.getId(), post.getTitle(), post.getContent(),
        post.getLikeTotalCount(), postImages, postTags,
        post.getCreatedDate(), post.getModifiedDate(), writer.getId(),
        writer.getName(), post.isPrivate());
  }

  public static List<PostInfo> from(List<Post> posts) {
    return posts.stream()
        .map(PostInfo::from)
        .collect(Collectors.toList());
  }


}
