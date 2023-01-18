package com.spring.boot.post.infrastructure;

import static com.spring.boot.connection.domain.QConnection.connection;
import static com.spring.boot.member.domain.QMember.member;
import static com.spring.boot.post.domain.QPost.post;
import static com.spring.boot.post.domain.tag.QPostTag.postTag;
import static com.spring.boot.tag.domain.QTag.tag;

import com.querydsl.core.BooleanBuilder;
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


  private List<Long> getMemberFollowings(Long memberId) {
    return queryFactory
        .select(connection.targetMember.id)
        .from(connection)
        .where(connection.member.id.eq(memberId))
        .fetch();
  }

  @Override
  public List<Post> getHomeFeedPost(Long memberId, int pageSize, Long postId) {
    List<Long> followingsAndReader = getMemberFollowings(memberId);
    followingsAndReader.add(memberId);

    BooleanBuilder builder = new BooleanBuilder();
    if (postId != null) {
      builder.and(post.id.lt(postId));
    }

    return queryFactory
        .selectFrom(post)
        .leftJoin(post.writer, member).fetchJoin()
        .where(builder.and(post.writer.id.in(followingsAndReader)))
        .distinct()
        .orderBy(post.id.desc())
        .limit(pageSize)
        .fetch();
  }

  @Override
  public List<Post> findFollowingsPost(Long memberId, Pageable pageable) {
    List<Long> followings = getMemberFollowings(memberId);

    return queryFactory
        .selectFrom(post)
        .join(post.writer)
        .fetchJoin()
        .where(post.writer.id.in(followings))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(post.id.desc())
        .fetch();
  }

  @Override
  public List<Post> findByMemberId(Long memberId, Pageable pageable) {
    return queryFactory
        .selectFrom(post)
        .join(post.writer, member)
        .fetchJoin()
        .where(post.writer.id.eq(memberId))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(post.id.desc())
        .fetch();
  }

  @Override
  public List<Post> findByTags(Set<Tag> tags, Pageable pageable) {
    return queryFactory
        .selectFrom(post)
        .join(post.writer, member).fetchJoin()
        .leftJoin(post.postTags.postTags, postTag)
        .leftJoin(postTag.tag, tag)
        .where(tag.in(tags))
        .distinct()
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(post.id.desc())
        .fetch();
  }

  @Override
  public Optional<Post> findByPostId(Long postId) {
    return Optional.ofNullable(queryFactory
        .selectFrom(post)
        .join(post.writer, member)
        .fetchJoin()
        .where(post.id.eq(postId))
        .fetchOne());
  }
}
