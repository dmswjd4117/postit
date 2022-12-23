package com.spring.boot.post.infrastructure;

import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {

  List<Post> findAll();

  List<Post> findAllFollowingsPost(Member member);

  Optional<Post> findByPostId(Long postId);

  List<Post> findAllByMember(Member findMember);

}
