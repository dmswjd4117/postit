package com.spring.boot.util;

import com.spring.boot.security.FormAuthentication;
import com.spring.boot.security.FormAuthenticationToken;
import java.util.Collections;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockFormAuthenticationSecurityContextFactory implements WithSecurityContextFactory<WithMockFormAuthenticationUser> {

  @Override
  public SecurityContext createSecurityContext(WithMockFormAuthenticationUser annotation) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();

    FormAuthentication principal = new FormAuthentication(annotation.id(), annotation.email(), annotation.name());

    Authentication auth = new FormAuthenticationToken(
        principal,
        "password",
        Collections.singletonList(new SimpleGrantedAuthority(annotation.grade()))
    );

    context.setAuthentication(auth);
    return context;
  }

}
