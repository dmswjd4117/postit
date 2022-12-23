package com.spring.boot.member.domain.role;

import com.spring.boot.member.domain.Member;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Getter
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

    protected MemberRole() {}

    private MemberRole(Member member, Role role){
        this.member = member;
        this.role = role;
    }
    public MemberRole(Role role){
        this(null, role);
    }

    public void changeMember(Member member) {
        this.member = member;
        member.getMemberRoles().add(this);
    }
    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .build();
    }

}
