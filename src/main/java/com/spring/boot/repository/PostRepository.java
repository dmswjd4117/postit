package com.spring.boot.repository;

import com.spring.boot.domain.Member;
import com.spring.boot.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByMember(Member findMember);
}
