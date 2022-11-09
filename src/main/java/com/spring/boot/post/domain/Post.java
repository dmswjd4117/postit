package com.spring.boot.post.domain;

import com.spring.boot.common.domain.BaseTime;
import com.spring.boot.member.domain.member.Member;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseTime {

  @Id
  @Column(name = "post_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String title;

  @Column
  private String body;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @BatchSize(size = 1000)
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
  private List<Image> images = new ArrayList<>();

  @OneToMany(mappedBy = "post")
  private Set<PostTag> postTags = new HashSet<>();


  public Post(String title, String body, Member member) {
    this.title = title;
    this.body = body;
    this.member = member;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("title", title)
        .append("body", body)
        .toString();
  }
}
