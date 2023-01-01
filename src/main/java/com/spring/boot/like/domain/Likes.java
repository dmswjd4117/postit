package com.spring.boot.like.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import org.hibernate.annotations.BatchSize;

@Embeddable
public class Likes {

  @BatchSize(size = 1000)
  @OneToMany(mappedBy = "post")
  private List<Like> likes = new ArrayList<>();

  public List<Like> getLikes() {
    return likes;
  }
}
