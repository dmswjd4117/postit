package com.spring.boot.post.domain.tag;

import com.spring.boot.post.domain.Post;
import com.spring.boot.tag.domain.Tag;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import org.hibernate.annotations.BatchSize;

@Embeddable
public class PostTags {

  @BatchSize(size = 1000)
  @OneToMany(
      mappedBy = "post",
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      orphanRemoval = true
  )
  private Set<PostTag> postTags = new HashSet<>();

  public void init(Set<Tag> tags, Post post) {
    Set<PostTag> newPostTags = tags.stream().map(tag -> new PostTag(tag, post))
        .collect(Collectors.toSet());
    postTags.clear();
    postTags.addAll(newPostTags);
  }

  public Set<PostTag> getPostTags() {
    return postTags;
  }
}
