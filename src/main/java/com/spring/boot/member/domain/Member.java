package com.spring.boot.member.domain;

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
  private List<Connection> following;

  @OneToMany(mappedBy = "targetMember")
  private List<Connection> followers;

  @Column
  @OneToMany(mappedBy = "writer")
  private List<Post> posts = new ArrayList<>();
  @JoinColumn(name = "role_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Role role;

  public Member(String email, String password, String name) {
    this.email = email;
    this.password = password;
    this.name = name;
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

  public boolean checkPassword(PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(this.password, password);
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
    if (!(o instanceof Member)) {
      return false;
    }
    Member member = (Member) o;
    return id.equals(member.getId()) && email.equals(member.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
