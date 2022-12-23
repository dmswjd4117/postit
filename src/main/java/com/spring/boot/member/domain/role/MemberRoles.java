package com.spring.boot.member.domain.role;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Embeddable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Embeddable
public class MemberRoles {

  private List<MemberRole> memberRoles;


  public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
    return memberRoles
        .stream()
        .map(MemberRole::getRole)
        .map(Role::getRoleName)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}
