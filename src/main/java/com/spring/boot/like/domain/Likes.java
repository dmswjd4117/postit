package com.spring.boot.like.domain;

import com.spring.boot.common.domain.BaseTime;
import com.spring.boot.member.domain.member.Member;

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
