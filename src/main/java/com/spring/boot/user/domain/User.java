package com.spring.boot.user.domain;

import com.spring.boot.common.BaseTime;
import com.spring.boot.connection.domain.Connection;
import com.spring.boot.post.domain.Post;
import com.spring.boot.role.domain.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends BaseTime {

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

  @OneToMany(mappedBy = "user")
  private List<Connection> following;

  @OneToMany(mappedBy = "targetUser")
  private List<Connection> followers;

  @Column
  @OneToMany(mappedBy = "writer")
  private List<Post> posts = new ArrayList<>();
  @JoinColumn(name = "role_id")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Role role;

  public User(String email, String password, String name, Role role) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.role = role;
  }

  public void initRole(Role role) {
    this.role = role;
  }

  public GrantedAuthority getGrantedAuthority() {
    return role.getGrantedAuthority();
  }

  public void setProfileImagePath(String profileImagePath) {
    this.profileImagePath = profileImagePath;
  }

  public boolean checkPassword(String rawPassword, PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(rawPassword, this.password);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("password", "[password]")
        .append("name", name)
        .append("role", role)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return id.equals(user.getId()) && email.equals(user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
