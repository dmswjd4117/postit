package com.spring.boot.comment.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  Optional<Comment> findByIdAndWriterId(Long commentId, Long memberId);
}
