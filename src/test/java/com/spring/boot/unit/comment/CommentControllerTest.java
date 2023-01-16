package com.spring.boot.unit.comment;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.comment.application.CommentService;
import com.spring.boot.comment.application.dto.CommentDto;
import com.spring.boot.comment.domain.Comment;
import com.spring.boot.comment.presentation.dto.CommentRequest;
import com.spring.boot.common.config.InfrastructureTestConfiguration;
import com.spring.boot.common.formAuthentication.WithMockFormAuthenticationUser;
import com.spring.boot.common.mock.MockPost;
import com.spring.boot.common.response.ApiResult;
import com.spring.boot.member.domain.Member;
import com.spring.boot.role.domain.Role;
import com.spring.boot.role.domain.RoleName;
import com.spring.boot.security.FormAuthentication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(InfrastructureTestConfiguration.class)
class CommentControllerTest {

}