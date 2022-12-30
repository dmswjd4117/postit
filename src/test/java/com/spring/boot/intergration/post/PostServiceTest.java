package com.spring.boot.intergration.post;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.intergration.IntegrationTest;
import com.spring.boot.user.domain.User;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.application.dto.PostInfoDto;
import com.spring.boot.post.application.dto.PostInfoDto.PostTagInfoDto;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.infrastructure.PostRepository;
import com.spring.boot.post.presentaion.dto.request.PostCreateRequest;
import com.spring.boot.post.presentaion.dto.request.PostUpdateRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;


class PostServiceTest extends IntegrationTest {

  @Autowired
  private PostService postService;
  @Autowired
  private PostRepository postRepository;


  public Set<String> extractTagNames(Set<PostTagInfoDto> postTagInfoDtos) {
    return postTagInfoDtos.stream()
        .map(PostTagInfoDto::getName)
        .collect(Collectors.toSet());
  }

  @Nested
  @DisplayName("게시물 생성, 삭제, 수정")
  class 생성_삭제_수정 {

    @Test
    @DisplayName("글 작성자는 게시물을 지울 수 있다")
    void 포스트_삭제() {
      // given
      User user = saveMember("email");
      Post createdPost = savePost(user);

      // when
      postService.deletePost(user.getId(), createdPost.getId());

      // then
      assertFalse(postRepository.findByPostId(createdPost.getId()).isPresent());
    }

    @Test
    @DisplayName("글 작성자가 아니면 포스트를 지울 수 없다")
    void 포스트_삭제_실패() {
      // given
      User writer = saveMember("email@naver.com");
      User user = saveMember("member@naver.com");
      Post createdPost = savePost(writer);

      // when
      assertThrows(AccessDeniedException.class, () -> {
        postService.deletePost(user.getId(), createdPost.getId());
      });
    }

    @Test
    @DisplayName("글 제목, 내용, 태그들을 수정할 수 있다.")
    void 포스트_수정() {
      // given
      String newTitle = "new title";
      String newContent = "new content";
      List<String> newTagNames = Arrays.asList("tag1", "newa", "newb");
      List<MultipartFile> newImages = Collections.singletonList(
          new MockMultipartFile("new", "new.jpg", IMAGE_PNG_VALUE, "new".getBytes())
      );

      User writer = saveMember("email@naver.com");
      Post createdPost = savePost(writer);

      // when
      PostUpdateRequest postUpdateRequest = new PostUpdateRequest(newTitle, newContent,
          newTagNames);
      PostInfoDto updated = postService.updatePost(writer.getId(), createdPost.getId(), postUpdateRequest, newImages);

      // then
      assertThat(updated, is(notNullValue()));
      assertThat(updated.getContent(), is(newContent));
      assertThat(updated.getWriter().getId(), is(writer.getId()));
      assertThat(updated.getImages().size(), is(newImages.size()));
      assertThat(updated.getTags().size(), is(newTagNames.size()));
      assertThat(extractTagNames(updated.getTags()), is(new HashSet<>(newTagNames)));
    }

    @Test
    @DisplayName("잘못된 유저 아이디일 경우 게시물을 생성할 수 없다.")
    void 포스트_생성_실패_잘못된_유저아이디() {
      PostCreateRequest postCreateRequest = new PostCreateRequest("title", "body",
          Collections.emptyList());

      assertThrows(NotFoundException.class, () -> {
        postService.createPost(-1L, postCreateRequest, Collections.emptyList());
      });
    }
  }


}