package com.spring.boot.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockFormAuthenticationSecurityContextFactory.class)
public @interface WithMockFormAuthenticationUser {

  long id() default 1L;

  String email() default "test@gmail.com";

  String grade() default "MEMBER";

  String name() default "test";
}
