package com.spring.boot.security;

import com.spring.boot.domain.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println(s);
        Collection<SimpleGrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority(Role.ADMIN.name()));

        return new User("user", "$2a$10$arnO0SDbGeFcPVDRwOzsGu4Oeg8ngSz68TOyU2tTgu/BykndDGjpa",
                new NullAuthoritiesMapper().mapAuthorities(collection));
    }
}
