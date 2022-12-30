package com.spring.boot.comment.domain;

import com.spring.boot.user.domain.User;
import com.spring.boot.post.domain.Post;
import lombok.Getter;

import javax.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @Column(name = "comment_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "writer_id")
    @ManyToOne
    private User writer;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    @Column
    private String body;

    public Comment(User writer, Post post, String body) {
        this.writer = writer;
        this.post = post;
        this.body = body;
    }

}

