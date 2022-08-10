package com.spring.boot.domain.member;

import com.spring.boot.domain.BaseTime;
import com.spring.boot.domain.connection.Connections;
import com.spring.boot.domain.like.Likes;
import com.spring.boot.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class Member extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column
    private String profileImagePath;

    @OneToMany(mappedBy = "member")
    private List<Connections> following;

    @OneToMany(mappedBy = "targetMember")
    private List<Connections> followers;

    @Column
    @OneToMany(mappedBy = "member")
    private List<Likes> likes = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberRole> memberRoles = new ArrayList<>();

    public Member(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        return getMemberRoles()
                .stream()
                .map(MemberRole::getRole)
                .map(Role::getRoleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("password", "[password]")
                .append("name", name)
                .toString();
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}
