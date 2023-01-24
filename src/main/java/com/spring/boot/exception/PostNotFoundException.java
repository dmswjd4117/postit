package com.spring.boot.exception;

import com.spring.boot.post.domain.Post;

public class PostNotFoundException extends NotFoundException{

  public PostNotFoundException(Long postId) {
    super(Post.class, "존재하지 않는 게시물입니다.", "아이디:", postId);
  }
}
