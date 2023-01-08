package com.spring.boot.intergration;

import com.spring.boot.common.DatabaseCleanUp;
import com.spring.boot.common.config.InfrastructureTestConfiguration;
import com.spring.boot.common.mock.MockMember;
import com.spring.boot.common.mock.MockPost;
import com.spring.boot.member.domain.Member;
import com.spring.boot.member.domain.UserRepository;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.infrastructure.PostRepository;
import com.spring.boot.role.domain.Role;
import com.spring.boot.role.domain.RoleName;
import com.spring.boot.role.domain.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(InfrastructureTestConfiguration.class)
public abstract class IntegrationTest {

  @Autowired
  DatabaseCleanUp databaseCleanUp;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PostRepository postRepository;
  @Autowired
  private RoleRepository roleRepository;


  @BeforeEach
  void cleanUp() {
    databaseCleanUp.clear();
  }

  protected Member saveMember(String email) {
    Role role = roleRepository.findByRoleName(RoleName.MEMBER.getValue())
        .orElseGet(() -> roleRepository.save(new Role(RoleName.MEMBER.getValue(), "member")));

    Member member = MockMember.builder(email, role)
        .build();

    return userRepository.save(member);
  }

  protected Post savePost(Member writer) {
    Post post = MockPost.builder("title", "content", writer)
        .build();

    return postRepository.save(post);
  }

}

