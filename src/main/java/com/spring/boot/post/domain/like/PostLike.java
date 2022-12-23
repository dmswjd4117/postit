package com.spring.boot.post.domain.like;

import com.spring.boot.common.BaseTime;
import com.spring.boot.member.domain.Member;

import com.spring.boot.post.domain.Post;
import javax.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "likes")
@Getter
public class PostLike extends BaseTime {
    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
