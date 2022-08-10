package com.spring.boot.repository;

import com.spring.boot.domain.member.Member;
import com.spring.boot.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select distinct p from Post p left join fetch p.images")
    List<Post> findByMemberWithImages(Member findMember);
}

