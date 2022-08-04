package com.spring.boot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

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

    @OneToMany(mappedBy = "post")
    private List<Image> images;

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Post(String title, String body, Member member){
        this.title = title;
        this.body = body;
        this.member = member;
    }
}
