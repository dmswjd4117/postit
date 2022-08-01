package com.spring.boot.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
public class MemberRole {

    @Id
    @Column(name = "member_role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;

    @JoinColumn(name = "role_id")
    @ManyToOne
    private Role role;

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .build();
    }
}
