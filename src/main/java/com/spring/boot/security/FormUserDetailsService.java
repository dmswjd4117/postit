package com.spring.boot.security;

import com.spring.boot.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class FormUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public FormUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(findMember -> {
                    Collection<? extends GrantedAuthority> auths = findMember.getGrantedAuthorities();
                    return new User(findMember.getEmail(), findMember.getPassword(), authoritiesMapper.mapAuthorities(auths));
                })
                .orElseThrow(()->new UsernameNotFoundException("non found email: "+email));
    }
}

