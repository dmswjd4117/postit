package com.spring.boot.comment.domain;

import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Comments {

  @OneToMany
  private List<Comment> comments;

  public int getSize() {
    return comments.size();
  }

  public List<Comment> getComments() {
    return comments;
  }
}
