package com.spring.boot.sampleData;

import com.spring.boot.connection.application.ConnectionService;
import com.spring.boot.post.domain.Post;
import com.spring.boot.role.application.RoleService;
import com.spring.boot.role.domain.Role;
import com.spring.boot.role.domain.RoleName;
import com.spring.boot.tag.application.TagService;
import com.spring.boot.tag.domain.Tag;
import com.spring.boot.member.domain.Member;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
public class init {

  private final InitService initService;


  public init(InitService initService) {
    this.initService = initService;
  }

  @PostConstruct
  public void init() {
    initService.init();
  }

  @Component
  static class InitService {

    private final EntityManager entityManager;
    private final RoleService roleService;
    private final TagService tagService;
    private final ConnectionService connectionService;
    private final PasswordEncoder passwordEncoder;

    InitService(EntityManager entityManager, RoleService roleService,
        TagService tagService, ConnectionService connectionService, PasswordEncoder passwordEncoder) {
      this.entityManager = entityManager;
      this.roleService = roleService;
      this.tagService = tagService;
      this.connectionService = connectionService;
      this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void init() {
      Role role = roleService.getRole(RoleName.MEMBER);
      Set<Tag> tags = tagService.saveTags(List.of("tag1", "tag2", "tag3"));
      List<String> images = List.of("image_url1", "image_url2");
      for (int i = 0; i < 5; i++) {
        // member
        String name = i + "name";
        String email = i + "email@gmail.com";
        Member member = new Member(
            email,
            passwordEncoder.encode("password"),
            name,
            role
        );
        entityManager.persist(member);

        // post
        for (int j = 0; j < 3; j++) {
          Post post = new Post(j+"", j+"", member);
          post.initPostTags(tags);
          post.initImages(images);
          entityManager.persist(post);
        }
      }

      connectionService.follow(2L, 1L);
      connectionService.follow(2L, 3L);
      connectionService.follow(2L, 4L);
      connectionService.follow(2L, 5L);
    }
  }


}
