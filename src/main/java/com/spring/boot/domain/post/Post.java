package com.spring.boot.domain.post;

import com.spring.boot.domain.BaseTime;
import com.spring.boot.domain.image.Image;
import com.spring.boot.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Member getMember() {
        return member;
    }

    public List<Image> getImages() {
        return images;
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
