package com.spring.boot.post.domain;

import com.spring.boot.common.domain.BaseTime;
import com.spring.boot.member.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseTime {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String body;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<Image> images;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private Set<PostTag> postTags = new HashSet<>();

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setPostTags(Set<PostTag> postTags) {
        this.postTags =  postTags;
    }

    public Post(String title, String body, Member member){
        this.title = title;
        this.body = body;
        this.member = member;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("title", title)
                .append("body", body)
                .append("member", member)
                .append("images", "images")
                .toString();
    }
}
