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
public class PostImages {

  @BatchSize(size = 1000)
  @OneToMany(
      mappedBy = "post",
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      orphanRemoval = true
  )
  private List<PostImage> postImages = new ArrayList<>();

  public void init(List<String> imagePaths, Post post) {
    List<PostImage> newPostImages = imagePaths.stream()
        .map(path -> new PostImage(path, post))
        .collect(Collectors.toList());
    postImages.clear();
    postImages.addAll(newPostImages);
  }

  public List<PostImage> getPostImages() {
    return postImages;
  }
}
