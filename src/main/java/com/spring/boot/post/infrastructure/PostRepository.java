package com.spring.boot.post.infrastructure;

import com.spring.boot.post.domain.Post;
import com.spring.boot.post.infrastructure.PostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
//  @Query("select distinct p " +
//      "from Post p " +
//      "where p.writer = :findMember")
//  List<Post> findAllByMember(Member findMember);
//
//  @Query("select distinct p " +
//      "from Post p " +
//      "left join fetch p.writer " +
//      "where p.id = :postId")
//  Optional<Post> findByPostId(Long postId);

//  @Query("select p "
//      + "from Post p join fetch p.member m "
//      + "where m in "
//      + "("
//      + "select t from Connection c inner join c.targetMember t "
//      + "where c.member = :member"
//      + ") "
//      + "or m = :member "
//      + "order by p.createdDate desc")
//  List<Post> findAllFollowingsPost(@Param("member") Member member, Pageable pageable);
}

