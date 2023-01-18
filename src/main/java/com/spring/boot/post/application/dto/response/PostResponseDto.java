package com.spring.boot.post.application.dto.response;


import com.spring.boot.comment.application.dto.CommentResponseDto;
import com.spring.boot.member.application.dto.MemberResponseDto;
import com.spring.boot.post.domain.Post;
import com.spring.boot.tag.application.dto.TagResponseDto;
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
public class PostResponseDto {

  private final Long id;
  private final String title;
  private final String content;
  private final int likeCount;
  private final List<PostImageResponseDto> images;
  private final Set<TagResponseDto> tags;
  private final LocalDateTime createdDate;
  private final LocalDateTime modifiedDate;
  private final MemberResponseDto writer;
  private final boolean isPrivate;
  private final List<CommentResponseDto> postComments;


  public static PostResponseDto from(Post post){
    List<PostImageResponseDto> images = PostImageResponseDto.from(post.getPostImages());
    Set<TagResponseDto> tags = TagResponseDto.from(post.getPostTags());
    MemberResponseDto writer = MemberResponseDto.from(post.getWriter());
    List<CommentResponseDto> comments = CommentResponseDto.from(post.getComments());

    return PostResponseDto.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .likeCount(post.getLikeTotalCount())
        .images(images)
        .tags(tags)
        .createdDate(post.getCreatedDate())
        .modifiedDate(post.getModifiedDate())
        .writer(writer)
        .isPrivate(post.isPrivate())
        .postComments(comments)
        .build();
  }

  public static List<PostResponseDto> from(List<Post> posts) {
    return posts.stream()
        .map(PostResponseDto::from)
        .collect(Collectors.toList());
  }



  @Getter
  public static class PostImageResponseDto {

    private Long id;
    private String imagePath;

    private PostImageResponseDto(Long id, String imagePath) {
      this.id = id;
      this.imagePath = imagePath;
    }

    public static List<PostImageResponseDto> from(
        List<com.spring.boot.post.domain.image.PostImage> postImages) {
      return postImages
          .stream()
          .map(postImage -> new PostImageResponseDto(postImage.getId(), postImage.getImagePath()))
          .collect(Collectors.toList());
    }
  }

}
