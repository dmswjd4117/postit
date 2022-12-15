package com.spring.boot.post.domain.tag;

import com.spring.boot.post.domain.Post;
import com.spring.boot.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTag {
    @Id
    @Column(name = "post_tag_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public PostTag(Tag tag) {
        this.tag = tag;
    }

    public void changePost(Post post) {
        this.post = post;
        post.getPostTags().add(this);
    }

    public String getTagName() {
        return tag.getTagName();
    }

    public Tag getTag() {
        return tag;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("post_tag_id", id)
                .toString();
    }
}
