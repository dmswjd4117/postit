package com.spring.boot.like.domain;

import com.spring.boot.common.BaseTime;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes", indexes = {
    @Index(name = "uniqueMultiIndex", columnList = "member_id, post_id", unique = true)
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseTime {

  @Id
  @Column(name = "like_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

  public Like(Member member, Post post) {
    this.member = member;
    this.post = post;
  }
}
