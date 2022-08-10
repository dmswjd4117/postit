package com.spring.boot.domain.member;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column
    private String roleName;

    @Column
    private String roleDescription;

    @OneToMany(mappedBy = "role")
    private final List<MemberRole> memberRoles = new ArrayList<>();

    public String getRoleName() {
        return roleName;
    }
    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("role_name", roleName)
                .append("role_description", roleDescription)
                .build();
    }
}
