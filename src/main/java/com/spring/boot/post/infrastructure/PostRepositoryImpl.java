package com.spring.boot.post.infrastructure;

import static com.spring.boot.connection.domain.QConnection.connection;
import static com.spring.boot.user.domain.QMember.member;
import static com.spring.boot.post.domain.QPost.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.boot.post.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public class PostRepositoryImpl implements PostRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public PostRepositoryImpl(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  @Override
  public List<Post> findAll() {
    return queryFactory.selectFrom(post)
        .fetch();
  }

  @Override
  public List<Post> findAllFollowingsPost(Long memberId, Pageable pageable) {

    List<Long> followings = queryFactory
        .select(connection.targetMember.id)
        .from(connection)
        .where(connection.member.id.eq(memberId))
        .fetch();

    return queryFactory
        .selectFrom(post)
        .join(post.writer)
        .fetchJoin()
        .where(post.writer.id.in(followings))
        .fetch();
  }

  @Override
  public Optional<Post> findByPostId(Long postId) {
    if(postId == null){
      throw new IllegalArgumentException("postId가 null입니다");
    }
    return Optional.ofNullable(queryFactory
        .selectFrom(post)
        .join(post.writer, member)
        .fetchJoin()
        .where(post.id.eq(postId))
        .fetchOne());
  }

  @Override
  public List<Post> findAllByMemberId(Long memberId) {
    return queryFactory
        .selectFrom(post)
        .join(post.writer, member)
        .fetchJoin()
        .where(post.writer.id.eq(memberId))
        .fetch();
  }
}
