package com.spring.boot.common.mock;

import com.spring.boot.like.domain.Like;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.domain.image.PostImages;
import com.spring.boot.post.domain.tag.PostTags;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockPost {

  public static Builder builder(String title, String content, Member writer) {
    return new Builder()
        .title(title)
        .content(content)
        .writer(writer);
  }

  public static class Builder {

    private Long id;
    private String title;
    private String content;
    private Member writer;
    private PostImages postImages = new PostImages();
    private PostTags postTags = new PostTags();
    private List<Like> likes = new ArrayList<>();

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }


    public Builder content(String content) {
      this.content = content;
      return this;
    }

    public Builder writer(Member writer) {
      this.writer = writer;
      return this;
    }

    public Builder postImages(PostImages postImages) {
      this.postImages = postImages;
      return this;
    }

    public Builder postTags(PostTags postTags) {
      this.postTags = postTags;
      return this;
    }

    public Builder postLikes(List<Like> likes) {
      this.likes = likes;
      return this;
    }

    public Post build() {
      return new Post(
          title, content, writer
      );
    }
  }

}
