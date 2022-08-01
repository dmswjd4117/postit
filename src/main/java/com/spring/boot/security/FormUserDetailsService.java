package com.spring.boot.security;

import com.spring.boot.domain.RoleName;
import com.spring.boot.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FormUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public FormUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(findMember -> {
                    System.out.println(findMember);
                    Collection<? extends GrantedAuthority> auths = findMember.getGrantedAuthorities();
                    return new User(findMember.getEmail(), null, authoritiesMapper.mapAuthorities(auths));
                })
                .orElseThrow(()->new RuntimeException("존재하지 않는 email 입니다"));
    }
}

