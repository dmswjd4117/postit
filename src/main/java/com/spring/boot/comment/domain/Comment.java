package com.spring.boot.comment.domain;

import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

  @Id
  @Column(name = "comment_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "writer_id")
  private Member writer;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

  @Column
  private String body;

  public Comment(Member writer, Post post, String body) {
    this.writer = writer;
    this.post = post;
    this.body = body;
  }

}

