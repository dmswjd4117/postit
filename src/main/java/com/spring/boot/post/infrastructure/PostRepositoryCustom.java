package com.spring.boot.post.infrastructure;

import com.spring.boot.post.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

  List<Post> findAll();

  List<Post> findAllFollowingsPost(Long memberId, Pageable pageable);

  Optional<Post> findByPostId(Long postId);

  List<Post> findAllByMemberId(Long memberId);

}
