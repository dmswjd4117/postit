package com.spring.boot.common.mock;

import com.spring.boot.connection.domain.Connection;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.domain.Post;
import com.spring.boot.role.domain.Role;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockMember {

  public static Builder builder(String email, String name, String password) {
    return new Builder()
        .email(email)
        .name(name)
        .password(password);
  }

  public static class Builder {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String profileImagePath;
    private List<Connection> following = new ArrayList<>();
    private List<Connection> followers = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();
    private Role role;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder profileImagePath(String profileImagePath) {
      this.profileImagePath = profileImagePath;
      return this;
    }

    public Builder following(List<Connection> following) {
      this.following = following;
      return this;
    }

    public Builder followers(List<Connection> followers) {
      this.following = followers;
      return this;
    }

    public Builder post( List<Post> posts) {
      this.posts = posts;
      return this;
    }

    public Builder email(Role role) {
      this.role = role;
      return this;
    }

    public Member build() {
      return new Member(
          id,
          email,
          password,
          name,
          profileImagePath,
          following,
          followers,
          posts,
          role
      );
    }

  }


}
