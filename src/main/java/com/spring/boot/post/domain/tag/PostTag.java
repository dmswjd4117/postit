package com.spring.boot.post.domain.tag;

import com.spring.boot.post.domain.Post;
import com.spring.boot.tag.domain.Tag;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTag {

  @Id
  @Column(name = "post_tag_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "tag_id")
  private Tag tag;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  public PostTag(Tag tag, Post post) {
    this.tag = tag;
    this.post = post;
  }

  public String getTagName() {
    return tag.getTagName();
  }

  public Long getId() {
    return id;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("post", post)
        .append("tag", tag)
        .toString();
  }
}
