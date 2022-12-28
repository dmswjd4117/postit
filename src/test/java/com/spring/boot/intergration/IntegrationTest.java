package com.spring.boot.intergration;

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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@ActiveProfiles("test")
@Import(InfrastructureTestConfiguration.class)
public abstract class IntegrationTest {

  @Autowired
  DatabaseCleanUp databaseCleanUp;

  @BeforeEach
  void cleanUp() {
    databaseCleanUp.clear();
  }



}

