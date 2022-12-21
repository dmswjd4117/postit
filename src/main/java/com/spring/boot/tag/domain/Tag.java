package com.spring.boot.tag.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

  @Id
  @Column(name = "tag_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String tagName;

  public Tag(String tagName) {
    this.tagName = tagName;
  }

  public String getTagName() {
    return tagName;
  }

  public Long getId() {
    return id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Tag)) {
      return false;
    }
    Tag tag = (Tag) o;
    return id.equals(tag.getId()) && tagName.equals(tag.tagName);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("tag name", tagName)
        .append("id", id)
        .build();
  }
}
