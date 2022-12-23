package com.spring.boot.post.infrastructure;

import static com.spring.boot.connection.domain.QConnection.connection;
import static com.spring.boot.member.domain.QMember.member;
import static com.spring.boot.post.domain.QPost.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import java.util.List;
import java.util.Optional;

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
  public List<Post> findAllFollowingsPost(Member member) {
    List<Member> followings = queryFactory
        .selectFrom(connection.targetMember)
        .where(connection.member.eq(member))
        .fetch();

    return queryFactory
        .selectFrom(post)
        .where(post.writer.in(followings))
        .fetch();
  }

  @Override
  public Optional<Post> findByPostId(Long postId) {
    return Optional.ofNullable(queryFactory
        .selectFrom(post)
        .where(post.id.eq(postId))
        .join(post.writer, member)
        .fetchJoin()
        .fetchOne());
  }

  @Override
  public List<Post> findAllByMember(Member member) {
    return queryFactory
        .selectFrom(post)
        .where(post.writer.eq(member))
        .fetch();
  }
}
