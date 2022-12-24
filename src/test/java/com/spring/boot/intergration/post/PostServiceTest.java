package com.spring.boot.intergration.post;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.intergration.IntegrationTest;
import com.spring.boot.member.application.dto.MemberInfoDto;
import com.spring.boot.post.application.PostSearchService;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.application.dto.PostInfoDto;
import com.spring.boot.post.presentaion.dto.request.PostCreateRequest;
import com.spring.boot.post.presentaion.dto.request.PostUpdateRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;


class PostServiceTest extends IntegrationTest {

  @Autowired
  private PostService postService;
  @Autowired
  private PostSearchService postSearchService;

  @Test
  @DisplayName("포스트를 생성할 수 있다.")
  void 포스트_생성() {
    // given
    MemberInfoDto member = saveMember();

    // when
    PostInfoDto createdPost = savePost(member.getId());

    // then
    assertNotNull(createdPost);
    assertThat(createdPost.getContent(), is(CONTENT));
    assertThat(createdPost.getTitle(), is(TITLE));
    assertThat(createdPost.getTags().size(), is(TAG_NAMES.size()));
  }

  @Test
  @DisplayName("글 작성자는 포스트를 지울 수 있다")
  void 포스트_삭제() {
    // given
    MemberInfoDto member = saveMember();
    PostInfoDto createdPost = savePost(member.getId());

    // when
    postService.deletePost(member.getId(), createdPost.getId());
  }

  @Test
  @DisplayName("글 작성자가 아니면 포스트를 지울 수 없다")
  void 포스트_삭제_실패() {
    // given
    MemberInfoDto writer = saveMember();
    MemberInfoDto member = saveMember();
    PostInfoDto createdPost = savePost(writer.getId());

    // when
    assertThrows(AccessDeniedException.class, () -> {
      postService.deletePost(member.getId(), createdPost.getId());
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

    MemberInfoDto writer = saveMember();
    PostInfoDto createdPost = savePost(writer.getId());

    // when
    PostUpdateRequest postUpdateRequest = new PostUpdateRequest(newTitle, newContent, newTagNames);
    postService.updatePost(writer.getId(), createdPost.getId(), postUpdateRequest, newImages);
    PostInfoDto updated = postSearchService.getPostByPostId(createdPost.getId());

    // then
    assertThat(updated.getTitle(), is(newTitle));
    assertThat(updated.getContent(), is(newContent));
    assertThat(updated.getImages().size(), is(newImages.size()));
    assertThat(updated.getWriter().getId(), equalTo(writer.getId()));
    assertThat(extractTagNames(updated.getTags()), is(new HashSet<>(newTagNames)));
  }

  @Test
  @DisplayName("잘못된 유저 아이디일 경우 생성할 수 없다.")
  void 포스트_생성_실패_잘못된_유저아이디() {
    PostCreateRequest postCreateRequest = new PostCreateRequest("title", "body",
        Collections.emptyList());

    assertThrows(NotFoundException.class, () -> {
      postService.createPost(-1L, postCreateRequest, Collections.emptyList());
    });
  }


}