package com.spring.boot.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class FormAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    private String credentials;

    public FormAuthenticationToken(String principal, String credentials){
        super(null);
        super.setAuthenticated(false);

        this.principal = principal;
        this.credentials = credentials;
    }

    public FormAuthenticationToken(Object principal, String credentials, Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        super.setAuthenticated(true);

        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
