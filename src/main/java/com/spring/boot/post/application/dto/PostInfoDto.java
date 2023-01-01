package com.spring.boot.post.application.dto;


import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.domain.image.PostImage;
import com.spring.boot.post.domain.tag.PostTag;
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
public class PostInfoDto {

  private Long id;
  private String title;
  private String content;
  private PostWriterDto writer;
  private int likeCount;
  private List<PostImageInfoDto> images;
  private Set<PostTagInfoDto> tags;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;

  public static PostInfoDto from(
      Post post) {

    PostWriterDto postWriter = PostWriterDto.from(post.getWriter());
    Set<PostTagInfoDto> postTagInfo = PostTagInfoDto.from(post.getPostTags());
    List<PostImageInfoDto> postImageInfo = PostImageInfoDto.from(post.getPostImages());

    return PostInfoDto.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .writer(postWriter)
        .likeCount(post.getLikes().size())
        .images(postImageInfo)
        .tags(postTagInfo)
        .createdDate(post.getCreatedDate())
        .modifiedDate(post.getModifiedDate())
        .build();
  }

  public static List<PostInfoDto> from(List<Post> posts) {
    return posts.stream()
        .map(PostInfoDto::from)
        .collect(Collectors.toList());
  }

  @Getter
  public static class PostWriterDto{
    private Long id;
    private String name;

    private PostWriterDto(Long id, String name) {
      this.id = id;
      this.name = name;
    }

    public static PostWriterDto from(Member member){
      return new PostWriterDto(member.getId(), member.getName());
    }
  }
  @Getter
  public static class PostImageInfoDto {
    private Long id;
    private String imagePath;

    private PostImageInfoDto(Long id, String imagePath) {
      this.id = id;
      this.imagePath = imagePath;
    }

    public static List<PostImageInfoDto> from(List<PostImage> postImages) {
      return postImages
          .stream()
          .map(postImage -> new PostImageInfoDto(postImage.getId(), postImage.getImagePath()))
          .collect(Collectors.toList());
    }
  }

  @Getter
  public static class PostTagInfoDto {
    private Long id;
    private String name;

    private PostTagInfoDto(Long id, String name) {
      this.id = id;
      this.name = name;
    }

    public static Set<PostTagInfoDto> from(Set<PostTag> postTags) {
      return postTags
          .stream()
          .map(postTag -> new PostTagInfoDto(postTag.getId(), postTag.getTagName()))
          .collect(Collectors.toSet());
    }
  }


}
