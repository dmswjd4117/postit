package com.spring.boot.post.domain;

import com.spring.boot.tag.application.dto.Tag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTag {
    @Id
    @Column(name = "post_tag_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public PostTag(Tag tag, Post post) {
        this.tag = tag;
        this.post = post;
    }

    public String getTagName() {
        return tag.getTagName();
    }

    public Long getId() {
        return id;
    }
}
