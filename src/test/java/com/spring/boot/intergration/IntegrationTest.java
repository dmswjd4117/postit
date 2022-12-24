package com.spring.boot.intergration;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import com.spring.boot.comment.application.CommentService;
import com.spring.boot.common.DatabaseCleanUp;
import com.spring.boot.common.config.InfrastructureTestConfiguration;
import com.spring.boot.connection.application.ConnectionService;
import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.application.dto.MemberInfoDto;
import com.spring.boot.post.application.PostSearchService;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.application.dto.PostInfoDto;
import com.spring.boot.post.application.dto.PostInfoDto.PostTagInfoDto;
import com.spring.boot.post.presentaion.dto.request.PostCreateRequest;
import com.spring.boot.role.domain.RoleName;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@ActiveProfiles("test")
@Import(InfrastructureTestConfiguration.class)
public abstract class IntegrationTest {

  @Autowired
  protected MemberService memberService;
  @Autowired
  protected PostService postService;
  @Autowired
  protected ConnectionService connectionService;
  @Autowired
  protected CommentService commentService;
  @Autowired
  protected PostSearchService postSearchService;
  protected String TITLE;
  protected String CONTENT;
  protected List<String> TAG_NAMES;
  protected List<MultipartFile> IMAGES;
  @Autowired
  DatabaseCleanUp databaseCleanUp;

  @BeforeEach
  void cleanUp() {
    databaseCleanUp.clear();
    TITLE = "title";
    CONTENT = "content";
    TAG_NAMES = Arrays.asList("tag1", "tag2");
    IMAGES = Arrays.asList(
        new MockMultipartFile("test", "test.jpg", IMAGE_PNG_VALUE, "test".getBytes()),
        new MockMultipartFile("test2", "test2.jpg", IMAGE_PNG_VALUE, "test2".getBytes())
    );
  }

  protected MemberInfoDto saveMember() {
    return memberService.register("name", RandomUtils.nextLong() + "email@gmail.com", "password",
        RoleName.MEMBER);
  }

  protected PostInfoDto savePost(Long memberId) {
    PostCreateRequest postCreateRequest = new PostCreateRequest(TITLE, CONTENT, TAG_NAMES);
    return postService.createPost(memberId, postCreateRequest, IMAGES);
  }


  protected Set<String> extractTagNames(Set<PostTagInfoDto> postTagInfoDtos) {
    return postTagInfoDtos.stream()
        .map(PostTagInfoDto::getName)
        .collect(Collectors.toSet());
  }

}

