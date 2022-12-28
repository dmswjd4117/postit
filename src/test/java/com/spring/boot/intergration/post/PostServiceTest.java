package com.spring.boot.intergration.post;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.common.factory.MemberFactory;
import com.spring.boot.connection.application.ConnectionService;
import com.spring.boot.intergration.IntegrationTest;
import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.application.dto.MemberInfoDto;
import com.spring.boot.member.domain.Member;
import com.spring.boot.member.domain.MemberRepository;
import com.spring.boot.post.application.PostSearchService;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.application.dto.PostInfoDto;
import com.spring.boot.post.application.dto.PostInfoDto.PostTagInfoDto;
import com.spring.boot.post.presentaion.dto.request.PostCreateRequest;
import com.spring.boot.post.presentaion.dto.request.PostUpdateRequest;
import com.spring.boot.role.domain.RoleName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;


class PostServiceTest extends IntegrationTest {

  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private PostService postService;
  @Autowired
  private PostSearchService postSearchService;
  @Autowired
  private ConnectionService connectionService;


  Member saveMember(String email) {
    Member member = MemberFactory.createMember(email, "name", "password");
    return memberRepository.save(member);
  }

  PostInfoDto savePost(Long memberId) {
    PostCreateRequest postCreateRequest = PostCreateRequest.builder()
        .title("title")
        .tagNames(Arrays.asList("tag1", "tag2"))
        .content("content")
        .build();

    List<MultipartFile> multipartFiles = Arrays.asList(
        new MockMultipartFile("test", "test.jpg", IMAGE_PNG_VALUE, "test".getBytes()),
        new MockMultipartFile("test2", "test2.jpg", IMAGE_PNG_VALUE, "test2".getBytes())
    );

    return postService.createPost(memberId, postCreateRequest, multipartFiles);
  }

  public static void checkSamePost(PostInfoDto post, PostInfoDto origin) {
    assertThat(post, is(notNullValue()));
    assertThat(post.getContent(), is(origin.getContent()));
    assertThat(post.getWriter().getId(), is(origin.getWriter().getId()));
    assertThat(post.getImages().size(), is(origin.getImages().size()));
    assertThat(post.getTags().size(), is(origin.getTags().size()));
    assertThat(extractTagNames(post.getTags()), is(extractTagNames(origin.getTags())));
  }

  public static Set<String> extractTagNames(Set<PostTagInfoDto> postTagInfoDtos) {
    return postTagInfoDtos.stream()
        .map(PostTagInfoDto::getName)
        .collect(Collectors.toSet());
  }

  @Test
  @DisplayName("글 작성자는 포스트를 지울 수 있다")
  void 포스트_삭제() {
    // given
    Member member = saveMember("email");
    PostInfoDto createdPost = savePost(member.getId());

    // when
    postService.deletePost(member.getId(), createdPost.getId());

    // then
    assertThrows(NotFoundException.class,
        () -> postSearchService.getPostByPostId(createdPost.getId()));
  }

  @Test
  @DisplayName("글 작성자가 아니면 포스트를 지울 수 없다")
  void 포스트_삭제_실패() {
    // given
    Member writer = saveMember("email@naver.com");
    Member member = saveMember("member@naver.com");
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

    Member writer = saveMember("email@naver.com");
    PostInfoDto createdPost = savePost(writer.getId());

    // when
    PostUpdateRequest postUpdateRequest = new PostUpdateRequest(newTitle, newContent, newTagNames);
    postService.updatePost(writer.getId(), createdPost.getId(), postUpdateRequest, newImages);
    PostInfoDto updated = postSearchService.getPostByPostId(createdPost.getId());

    // then
    assertThat(updated, is(notNullValue()));
    assertThat(updated.getContent(), is(newContent));
    assertThat(updated.getWriter().getId(), is(writer.getId()));
    assertThat(updated.getImages().size(), is(newImages.size()));
    assertThat(updated.getTags().size(), is(newTagNames.size()));
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

  @Test
  @DisplayName("팔로잉하고 있는 멤버들의 게시물들을 조회한다")
  void 팔로잉들의_게시물_조회() {
    // given
    Member member = saveMember("member@naver.com");

    for (int i = 0; i < 4; i++) {
      Member targetMember = saveMember(i + "email@naver.com");

      connectionService.follow(member.getId(), targetMember.getId());
      for (int j = 0; j < 2; j++) {
        savePost(targetMember.getId());
      }
    }

    // when
    Pageable pageable = Pageable.ofSize(2);
    List<PostInfoDto> findPosts = postSearchService.getAllFollowingsPost(member.getId(), pageable);
    assertThat(findPosts.size(), is(8));
  }

  @Test
  @DisplayName("포스트 아이디로 포스트를 조회할 수 있다.")
  void 게시물_게시물아이디로_조회() {
    // given
    Member member = saveMember("email@naver.com");
    PostInfoDto post = savePost(member.getId());

    // when
    PostInfoDto findPost = postSearchService.getPostByPostId(post.getId());

    // then
    checkSamePost(findPost, post);
  }


  @Test
  @DisplayName("멤버 아이디로 글들을 조회한다")
  void 게시물_멤버아이디로_조회() {
    int DUMMY_POST_CNT = 3;

    // given
    Member member = saveMember("email@naver.com");
    List<PostInfoDto> originPosts = new ArrayList<>();

    for (int i = 0; i < DUMMY_POST_CNT; i++) {
      PostInfoDto post = savePost(member.getId());
      originPosts.add(post);
    }

    // when
    List<PostInfoDto> findPosts = postSearchService.getPostByMemberId(
        member.getId());

    // then
    assertThat(findPosts.size(), is(DUMMY_POST_CNT));
    for (int index = 0; index < DUMMY_POST_CNT; index++) {
      checkSamePost(findPosts.get(index), originPosts.get(index));
    }
  }

  @Test
  @DisplayName("모든 게시물 조회한다")
  void 모든_게시물_조회() {
    // given
    Member member = saveMember("email@naver.com");

    for (int i = 0; i < 2; i++) {
      savePost(member.getId());
    }

    Member member2 = saveMember("email2@naver.com");
    for (int i = 0; i < 3; i++) {
      savePost(member2.getId());
    }

    // when
    List<PostInfoDto> posts = postSearchService.getAllPost();

    // then
    assertThat(posts.size(), is(5));
  }


}