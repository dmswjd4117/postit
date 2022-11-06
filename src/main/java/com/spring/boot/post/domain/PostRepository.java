package com.spring.boot.post.domain;

import com.spring.boot.member.domain.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

  @Query("select distinct p " +
      "from Post p " +
      "left join fetch p.postTags pt " +
      "left join fetch pt.tag " +
      "where p.member = :findMember")
  List<Post> findByMemberWithTags(Member findMember);

  @Query("select distinct p " +
      "from Post p " +
      "left join fetch p.postTags pt " +
      "left join fetch pt.tag " +
      "where p.id = :postId")
  Optional<Post> findByPostIdWithTags(Long postId);
}

