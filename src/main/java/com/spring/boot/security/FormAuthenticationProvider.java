package com.spring.boot.security;

import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.application.dto.UserInfoDto;
import java.util.Collection;
import java.util.List;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.transaction.annotation.Transactional;

public class FormAuthenticationProvider implements AuthenticationProvider {

  private final MemberService memberService;
  private final NullAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

  public FormAuthenticationProvider(MemberService memberService) {
    this.memberService = memberService;
  }

  @Transactional
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String email = (String) authentication.getPrincipal();
    String password = (String) authentication.getCredentials();

    UserInfoDto findMember = memberService.login(email, password);

    FormAuthentication formAuthentication = new FormAuthentication(
        findMember.getId(),
        findMember.getEmail(),
        findMember.getName()
    );

    Collection<? extends GrantedAuthority> auths = List.of(findMember.getGrantedAuthority());

    return new FormAuthenticationToken(formAuthentication, null,
        authoritiesMapper.mapAuthorities(auths));
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
