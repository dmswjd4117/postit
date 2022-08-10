package com.spring.boot.domain.like;

import com.spring.boot.domain.BaseTime;
import com.spring.boot.domain.member.Member;

import javax.persistence.*;

@Entity
public class Likes extends BaseTime {
    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
