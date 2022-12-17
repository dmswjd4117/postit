package com.spring.boot.post.domain;

import com.spring.boot.member.domain.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
  @Query("select distinct p " +
      "from Post p " +
      "where p.member = :findMember")
  List<Post> findByMember(Member findMember);

  @Query("select distinct p " +
      "from Post p " +
      "left join fetch p.member " +
      "where p.id = :postId")
  Optional<Post> findByPostIdWithMember(Long postId);

//  @Query("select p "
//      + "from Post p join fetch p.member m "
//      + "where m in "
//      + "("
//      + "select t from Connection c inner join c.targetMember t "
//      + "where c.member = :member"
//      + ") "
//      + "or m = :member "
//      + "order by p.createdDate desc")
//  List<Post> getAllFollowingsPost(@Param("member") Member member, Pageable pageable);
}

