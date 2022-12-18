package com.spring.boot.post.domain.image;

import com.spring.boot.post.domain.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import org.hibernate.annotations.BatchSize;

@Embeddable
public class Images {

  @BatchSize(size = 1000)
  @OneToMany(
      mappedBy = "post",
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      orphanRemoval = true
  )
  private List<Image> images = new ArrayList<>();

  public void init(List<String> imagePaths, Post post) {
    List<Image> newImages = imagePaths.stream()
        .map(path -> new Image(path, post))
        .collect(Collectors.toList());
    images.clear();
    images.addAll(newImages);
  }

  public List<Image> getPostImages() {
    return images;
  }
}
