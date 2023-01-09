package com.spring.boot.post.infrastructure;

import static com.spring.boot.connection.domain.QConnection.connection;
import static com.spring.boot.post.domain.QPost.post;
import static com.spring.boot.member.domain.QMember.member;
import static com.spring.boot.post.domain.tag.QPostTag.postTag;
import static com.spring.boot.tag.domain.QTag.tag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.boot.post.domain.Post;
import com.spring.boot.tag.domain.Tag;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public class PostRepositoryImpl implements PostRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public PostRepositoryImpl(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
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
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  @Override
  public List<Post> findAllByMemberId(Long memberId, Pageable pageable) {
    return queryFactory
        .selectFrom(post)
        .join(post.writer, member)
        .fetchJoin()
        .where(post.writer.id.eq(memberId))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  @Override
  public List<Post> searchByTags(Set<Tag> tags,  Pageable pageable) {
    return queryFactory
        .selectFrom(post)
        .join(post.writer, member).fetchJoin()
        .leftJoin(post.postTags.postTags, postTag)
        .leftJoin(postTag.tag, tag)
        .where(tag.in(tags))
        .distinct()
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  @Override
  public Optional<Post> findByPostId(Long postId) {
    if (postId == null) {
      throw new IllegalArgumentException("postId가 null입니다");
    }
    return Optional.ofNullable(queryFactory
        .selectFrom(post)
        .join(post.writer, member)
        .fetchJoin()
        .where(post.id.eq(postId))
        .fetchOne());
  }
}
