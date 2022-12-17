package com.spring.boot.image.domain;

import com.spring.boot.common.domain.BaseTime;
import com.spring.boot.post.domain.Post;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Image extends BaseTime {

  @Id
  @Column(name = "image_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String imagePath;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

  public Image(String imagePath) {
    this.imagePath = imagePath;
  }

  public void changePost(Post post) {
    this.post = post;
    this.post.getImages().add(this);
  }

  public Long getId() {
    return id;
  }

  public String getImagePath() {
    return imagePath;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("imagePath", imagePath)
        .append("post id", post.getId())
        .append("created", getCreatedDate())
        .append("modified", getModifiedDate())
        .toString();
  }
}
