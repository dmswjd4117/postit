package com.spring.boot.unit.post.presentaion;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.common.config.InfrastructureTestConfiguration;
import com.spring.boot.intergration.formAuthentication.WithMockFormAuthenticationUser;
import com.spring.boot.common.response.ApiResult;
import com.spring.boot.role.domain.Role;
import com.spring.boot.role.domain.RoleName;
import com.spring.boot.user.domain.Member;
import com.spring.boot.post.application.PostService;
import com.spring.boot.post.application.dto.PostInfoDto;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.presentaion.dto.response.PostInfoResponse;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(InfrastructureTestConfiguration.class)
class PostControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private PostService postService;


  @ParameterizedTest
  @DisplayName("제목이 null or 공백 or 빈 문자열 이면 포스트를 생성할 수 없다.")
  @WithMockFormAuthenticationUser
  @ValueSource(strings = {"", " "})
  @NullSource
  void 포스트_생성_실패(String title) throws Exception {
    // given
    String content = "content";
    Member member = new Member("email", "password", "name", new Role(RoleName.MEMBER.getValue(), "role"));
    PostInfoDto post = PostInfoDto.from(new Post(title, content, member));

    given(postService.createPost(any(), any(), any()))
        .willReturn(post);

    // when
    MockHttpServletResponse response = mockMvc.perform(
        multipart("/api/post")
            .param("title", title)
            .param("content", content)
    ).andReturn().getResponse();

    // then
    TypeReference<ApiResult<PostInfoResponse>> responseType = new TypeReference<ApiResult<PostInfoResponse>>() {
    };
    ApiResult<PostInfoResponse> apiResult = objectMapper.readValue(
        response.getContentAsString(), responseType);

    assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    assertNull(apiResult.getResponse());
    assertFalse(apiResult.isSuccess());
  }

  @Test
  @DisplayName("포스트 생성할 수 있다.")
  @WithMockFormAuthenticationUser
  void 포스트_생성_성공() throws Exception {
    // given
    String title = "title";
    String content = "content";
    Member member = new Member("email", "password", "name", new Role(RoleName.MEMBER.getValue(), "role"));
    PostInfoDto post = PostInfoDto.from(new Post(title, content, member));

    given(postService.createPost(any(), any(), any()))
        .willReturn(post);

    // then
    MockHttpServletResponse response = mockMvc.perform(
        multipart("/api/post")
            .param("title", title)
            .param("content", content)
            .param("tagNames", String.valueOf(new ArrayList<>()))
    ).andReturn().getResponse();

    // then
    TypeReference<ApiResult<PostInfoResponse>> responseType = new TypeReference<>() {
    };
    ApiResult<PostInfoResponse> ApiResultResponse = objectMapper.readValue(
        response.getContentAsString(), responseType);

    assertAll(
        () -> {
          assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
          assertThat(ApiResultResponse.getError()).isNull();
          assertThat(ApiResultResponse.isSuccess()).isTrue();

          assertAll(() -> {
            PostInfoResponse postInfoResponse = ApiResultResponse.getResponse();
            assertThat(postInfoResponse.getContent()).isEqualTo(post.getContent());
            assertThat(postInfoResponse.getTitle()).isEqualTo(post.getTitle());
          });
        }
    );
  }

}