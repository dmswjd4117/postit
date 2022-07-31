package com.spring.boot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member extends BaseTime{
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
    @OneToMany(mappedBy = "member")
    private List<Likes> likes = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    public Member(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("password", "[password]")
                .append("name", name)
                .toString();
    }
}
