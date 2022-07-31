package com.spring.boot.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Post extends BaseTime {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Image> images;
}
