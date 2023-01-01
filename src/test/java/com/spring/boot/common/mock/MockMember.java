package com.spring.boot.common.mock;

import com.spring.boot.connection.domain.Connection;
import com.spring.boot.user.domain.Member;
import com.spring.boot.post.domain.Post;
import com.spring.boot.role.domain.Role;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockMember {

  public static Builder builder(String email, String name, String password, Role role) {
    return new Builder()
        .email(email)
        .name(name)
        .password(password)
        .role(role);
  }

  public static class Builder {

    private Long id;
    private String email;
    private String password;
    private Role role;
    private String name = "test";
    private String profileImagePath = "https://i.ibb.co/4M4JvyT/2022-12-30-1-58-57.png";
    private List<Connection> following = new ArrayList<>();
    private List<Connection> followers = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();

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

    public Builder post(List<Post> posts) {
      this.posts = posts;
      return this;
    }

    public Builder role(Role role) {
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
