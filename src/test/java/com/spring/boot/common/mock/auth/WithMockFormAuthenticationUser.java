package com.spring.boot.common.mock.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockFormAuthenticationSecurityContextFactory.class)
public @interface WithMockFormAuthenticationUser {

  long id() default 1L;

  String email() default "test@gmail.com";

  String grade() default "ROLE_MEMBER";

  String name() default "test";
}
