package com.spring.boot.comment.domain;

import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import org.hibernate.annotations.BatchSize;

@Embeddable
public class Comments {

  @OneToMany
  @BatchSize(size = 1000)
  private List<Comment> comments;

  public int getSize() {
    return comments.size();
  }

  public List<Comment> getComments() {
    return comments;
  }
}
