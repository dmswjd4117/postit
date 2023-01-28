package com.spring.boot.sampleData;

import com.spring.boot.connection.application.ConnectionService;
import com.spring.boot.member.domain.Member;
import com.spring.boot.member.domain.MemberRepository;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.infrastructure.PostRepository;
import com.spring.boot.role.application.RoleService;
import com.spring.boot.role.domain.Role;
import com.spring.boot.role.domain.RoleName;
import com.spring.boot.tag.application.TagService;
import com.spring.boot.tag.domain.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    InitService(EntityManager entityManager, RoleService roleService,
        TagService tagService, ConnectionService connectionService, PasswordEncoder passwordEncoder,
        MemberRepository memberRepository, PostRepository postRepository) {
      this.entityManager = entityManager;
      this.roleService = roleService;
      this.tagService = tagService;
      this.connectionService = connectionService;
      this.passwordEncoder = passwordEncoder;
      this.memberRepository = memberRepository;
      this.postRepository = postRepository;
    }

    @Transactional
    public void init() {
      Role role = roleService.getRole(RoleName.MEMBER);
      Set<Tag> tags = tagService.saveTags(List.of("tag1", "tag2", "tag3"));
      List<String> images = List.of("image_url1", "image_url2");

      List<Member> members = new ArrayList<>();
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
        Member saved = memberRepository.save(member);
        members.add(saved);

        // post
        for (int j = 0; j < 3; j++) {
          Post post = new Post.Builder(j + "", j + "", saved).build();
          post.initPostTags(tags);
          post.initImages(images);
          postRepository.save(post);
        }
      }

      connectionService.follow(members.get(0).getId(), members.get(1).getId());
      connectionService.follow(members.get(0).getId(), members.get(2).getId());
      connectionService.follow(members.get(0).getId(), members.get(3).getId());
      connectionService.follow(members.get(0).getId(), members.get(4).getId());
    }
  }


}
