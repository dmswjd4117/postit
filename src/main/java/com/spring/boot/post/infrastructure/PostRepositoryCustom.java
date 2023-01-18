package com.spring.boot.post.infrastructure;

import com.spring.boot.post.domain.Post;
import com.spring.boot.tag.domain.Tag;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

  Optional<Post> findByPostId(Long postId);

  List<Post> findFollowingsPost(Long memberId, Pageable pageable);

  List<Post> findByMemberId(Long memberId, Pageable pageable);

  List<Post> findByTags(Set<Tag> tags, Pageable pageable);

  List<Post> getHomeFeedPost(Long memberId, int pageSize, Long postId);

}
