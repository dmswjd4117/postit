package com.spring.boot.post.domain;

import com.spring.boot.common.BaseTime;
import com.spring.boot.post.domain.image.Image;
import com.spring.boot.post.domain.image.Images;
import com.spring.boot.like.domain.Like;
import com.spring.boot.member.domain.member.Member;
import com.spring.boot.post.domain.tag.PostTag;
import com.spring.boot.post.domain.tag.PostTags;
import com.spring.boot.tag.domain.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

  @Id
  @Column(name = "post_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String title;

  @Column
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member writer;

  @Embedded
  private Images images = new Images();

  @Embedded
  private PostTags postTags = new PostTags();

  @BatchSize(size = 1000)
  @OneToMany(mappedBy = "post")
  private List<Like> likes = new ArrayList<>();

  public Post(String title, String content, Member writer) {
    this.title = title;
    this.content = content;
    this.writer = writer;
  }

  public void initPostTags(List<Tag> tags) {
    postTags.init(tags, this);
  }

  public void initImages(List<String> imagePaths) {
    images.init(imagePaths, this);
  }

  public boolean isWrittenBy(Member member) {
    return writer.equals(member);
  }

  public void updatePost(String title, String content, List<Tag> tags) {
    this.title = title;
    this.content = content;
    initPostTags(tags);
  }

  public Set<PostTag> getPostTags() {
    return postTags.getPostTags();
  }

  public List<Image> getImages() {
    return images.getPostImages();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("title", title)
        .append("body", content)
        .toString();
  }
}
