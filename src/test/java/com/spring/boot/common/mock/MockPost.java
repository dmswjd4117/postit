package com.spring.boot.common.mock;

import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockPost {

  public static final String title = "title";
  public static final String content = "content";
  public static Post create(Member member) {
    return new Post.Builder(title, content, member).build();
  }


}
