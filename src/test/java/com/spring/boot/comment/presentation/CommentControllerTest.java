package com.spring.boot.comment.presentation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.comment.application.CommentService;
import com.spring.boot.comment.domain.Comment;
import com.spring.boot.comment.presentation.dto.CommentRequest;
import com.spring.boot.comment.presentation.dto.CommentResponse;
import com.spring.boot.common.dto.ApiResult;
import com.spring.boot.member.domain.member.Member;
import com.spring.boot.post.domain.Post;
import com.spring.boot.security.FormAuthentication;
import com.spring.boot.util.mockAuthentication.WithMockFormAuthenticationUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CommentControllerTest {

  private final Member POST_WRITER = new Member("email@gmail.com", "password", "name");
  private final Post POST = new Post("title", "post-body", POST_WRITER);
  @MockBean
  private CommentService commentService;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @WithMockFormAuthenticationUser
  @Test
  @DisplayName("댓글 작성")
  void createComment() throws Exception {

    // given
    FormAuthentication principal = (FormAuthentication) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    Member commentWriter = new Member(principal.email, "password", principal.name);

    Comment comment = new Comment(
        commentWriter,
        POST,
        "comment-body"
    );

    given(commentService.createComment(any(), any(), any()))
        .willReturn(comment);

    // when
    MockHttpServletResponse response = mockMvc.perform(
        post("/api/comment")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new CommentRequest(comment.getBody(), 1L)))
    ).andReturn().getResponse();

    // then
    TypeReference<ApiResult<CommentResponse>> responseType = new TypeReference<ApiResult<CommentResponse>>() {
    };
    ApiResult<CommentResponse> ApiResultResponse = objectMapper.readValue(
        response.getContentAsString(), responseType);

    assertAll(
        () -> {
          assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
          assertThat(ApiResultResponse.getError()).isNull();
          assertThat(ApiResultResponse.isSuccess()).isTrue();

          assertAll(() -> {
            CommentResponse commentResponse = ApiResultResponse.getResponse();
            assertThat(commentResponse.getComment()).isEqualTo(comment.getBody());
            assertThat(commentResponse.getCommentWriter().getEmail()).isEqualTo(
                commentWriter.getEmail());
          });
        }
    );
  }

}