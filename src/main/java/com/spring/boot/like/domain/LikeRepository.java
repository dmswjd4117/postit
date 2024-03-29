package com.spring.boot.like.domain;


import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Long> {

  Optional<Like> findByMemberAndPost(Member member, Post post);

  List<Like> findByPost(Post post);
}
