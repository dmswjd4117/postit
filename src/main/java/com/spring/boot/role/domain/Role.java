package com.spring.boot.role.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id")
  private Long id;
  @Column
  private String roleName;
  @Column
  private String roleDescription;

  public Role(String value, String roleDescription) {
    this.roleName = value;
    this.roleDescription = roleDescription;
  }

  public GrantedAuthority getGrantedAuthority() {
    return new SimpleGrantedAuthority(getRoleName());
  }
  public String getRoleName() {
    return roleName;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("role_name", roleName)
        .append("role_description", roleDescription)
        .build();
  }
}
