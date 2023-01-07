package com.spring.boot.like.domain;


import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Long> {

  Optional<Like> findByMemberAndPost(Member member, Post post);

  @Query(value = "insert ignore into likes (member_id, post_id) values (:member_id, :post_id)",
      nativeQuery = true)
  Optional<Like> saveInsertIgnore(Long member_id, Long post_id);

  boolean existsByMemberAndPost(Member member, Post post);
}
