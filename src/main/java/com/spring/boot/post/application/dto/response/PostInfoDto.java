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
public class PostInfoDto {

  private final Long id;
  private final String title;
  private final String content;
  private final int likeCount;
  private final List<PostImage> images;
  private final Set<PostTag> tags;
  private final LocalDateTime createdDate;
  private final LocalDateTime modifiedDate;
  private final Long writerId;
  private final String writerName;
  private final String writerProfileImagePath;
  private final boolean isPrivate;


  public PostInfoDto(Long id, String title, String content, int likeCount,
      List<PostImage> images, Set<PostTag> tags, LocalDateTime createdDate,
      LocalDateTime modifiedDate, Long writerId, String writerName, String writerProfileImagePath,
      boolean isPrivate) {
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
    this.writerProfileImagePath = writerProfileImagePath;
    this.isPrivate = isPrivate;
  }

  public static PostInfoDto from(
      Post post) {

    Member writer = post.getWriter();
    Set<PostTag> postTags = PostTag.from(post.getPostTags());
    List<PostImage> postImages = PostImage.from(post.getPostImages());

    return new PostInfoDto(post.getId(), post.getTitle(), post.getContent(),
        post.getLikeTotalCount(), postImages, postTags,
        post.getCreatedDate(), post.getModifiedDate(), writer.getId(),
        writer.getName(), writer.getProfileImagePath(), post.isPrivate());
  }

  public static List<PostInfoDto> from(List<Post> posts) {
    return posts.stream()
        .map(PostInfoDto::from)
        .collect(Collectors.toList());
  }


  @Getter
  public static class PostImage {

    private Long id;
    private String imagePath;

    private PostImage(Long id, String imagePath) {
      this.id = id;
      this.imagePath = imagePath;
    }

    public static List<PostImage> from(
        List<com.spring.boot.post.domain.image.PostImage> postImages) {
      return postImages
          .stream()
          .map(postImage -> new PostImage(postImage.getId(), postImage.getImagePath()))
          .collect(Collectors.toList());
    }
  }

  @ToString
  @Getter
  public static class PostTag {

    private Long id;
    private String name;

    private PostTag(Long id, String name) {
      this.id = id;
      this.name = name;
    }

    public static Set<PostTag> from(Set<com.spring.boot.post.domain.tag.PostTag> postTags) {
      return postTags
          .stream()
          .map(postTag -> new PostTag(postTag.getId(), postTag.getTagName()))
          .collect(Collectors.toSet());
    }
  }
}
