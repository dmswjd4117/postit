package com.spring.boot.comment.presentation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.comment.application.CommentService;
import com.spring.boot.comment.domain.Comment;
import com.spring.boot.comment.presentation.dto.CommentRequestDto;
import com.spring.boot.comment.presentation.dto.CommentResponseDto;
import com.spring.boot.common.dto.ApiResult;
import com.spring.boot.member.domain.member.Member;
import com.spring.boot.post.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

  @MockBean
  private CommentService commentService;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  private final Member MEMBER = new Member("email@gmail.com", "password", "name");
  private final Post POST = new Post("title", "post-body", MEMBER);
  private final String COMMENT_BODY = "comment-body";

  @Test
  @DisplayName("댓글 작성")
  void createComment() throws Exception {
    Comment comment = new Comment(MEMBER, POST, COMMENT_BODY);
    given(commentService.createComment(MEMBER.getId(), COMMENT_BODY, POST.getId()))
        .willReturn(comment);

    // when
    MockHttpServletResponse response = mockMvc.perform(
          post("/api/comment")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new CommentRequestDto(COMMENT_BODY, POST.getId())))
        ).andReturn().getResponse();


    // then
    TypeReference<ApiResult<CommentResponseDto>> responseType = new TypeReference(){};
    ApiResult<CommentResponseDto> ApiResultResponse = objectMapper.readValue(response.getContentAsString(), responseType);

    assertAll(
        ()->{
          assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
          assertThat(ApiResultResponse.getError()).isNull();
          assertThat(ApiResultResponse.isSuccess()).isTrue();
          assertThat(ApiResultResponse.getResponse()).isNotNull();

          CommentResponseDto commentResponseDto = ApiResultResponse.getResponse();
        }
    );
  }

}