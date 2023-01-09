package com.spring.boot.post.infrastructure;

import com.spring.boot.post.domain.Post;
import com.spring.boot.tag.domain.Tag;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

  Optional<Post> findByPostId(Long postId);

  List<Post> findAllFollowingsPost(Long memberId, Pageable pageable);

  List<Post> findAllByMemberId(Long memberId, Pageable pageable);

  List<Post> searchByTags(Set<Tag> tags, Pageable pageable);

}
