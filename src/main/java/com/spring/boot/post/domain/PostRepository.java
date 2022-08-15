package com.spring.boot.post.domain;

import com.spring.boot.member.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p " +
            "from Post p " +
            "where p.member = :findMember")
    List<Post> findByMember(Member findMember);

//    @Query("select p " +
//            "from Post p " +
//            "left join fetch p.images " +
//            "left join fetch p.postTags pt " +
//            "where p.id = :postId")
//    Optional<Post> findByIdWithImageAndPostTag(Long postId);
}

