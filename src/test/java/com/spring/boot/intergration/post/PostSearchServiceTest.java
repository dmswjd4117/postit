package com.spring.boot.intergration.post;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

import com.spring.boot.intergration.IntegrationTest;
import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.application.dto.MemberDto;
import com.spring.boot.post.application.PostQueryService;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.application.dto.request.PostCreateDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

public class PostSearchServiceTest extends IntegrationTest {

  @Autowired
  PostQueryService postQueryService;

  @Autowired
  PostService postService;

  @Autowired
  MemberService memberService;


  @Test
  void 태그_검색() {
    MemberDto member = memberService.register("name", "email@email.com", "password");
    for (int i = 0; i < 3; i++) {
      PostCreateDto postCreateRequest = PostCreateDto.builder()
          .content("content")
          .title("title")
          .writerId(member.getId())
          .tagNames(List.of("one", "two"))
          .build();
      postService.createPost(postCreateRequest);
    }

    for (int i = 0; i < 4; i++) {
      PostCreateDto postCreateRequest = PostCreateDto.builder()
          .content("content")
          .title("title")
          .writerId(member.getId())
          .tagNames(List.of("content"))
          .build();
      postService.createPost(postCreateRequest);
    }

    // one, two
    assertThat(postQueryService.getPostByTags(List.of("one", "two"), Pageable.ofSize(10)).size(), is(3));

    // one, content
    assertThat(postQueryService.getPostByTags(List.of("one", "content"),  Pageable.ofSize(10)).size(), is(7));

    // content
    assertThat(postQueryService.getPostByTags(List.of("content"),  Pageable.ofSize(10)).size(), is(4));

    // one, two, content
    assertThat(postQueryService.getPostByTags(List.of("one", "two", "content"), Pageable.ofSize(10)).size(), is(7));
  }


}
