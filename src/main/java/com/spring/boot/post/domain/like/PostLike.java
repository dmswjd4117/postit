package com.spring.boot.post.domain.like;

import com.spring.boot.common.BaseTime;
import com.spring.boot.user.domain.User;

import com.spring.boot.post.domain.Post;
import javax.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "likeCount")
@Getter
public class PostLike extends BaseTime {
    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
