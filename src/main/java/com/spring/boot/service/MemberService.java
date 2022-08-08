package com.spring.boot.service;

import com.spring.boot.dto.member.MemberRegisterRequestDto;
import com.spring.boot.domain.Member;
import com.spring.boot.error.DuplicatedEmailException;
import com.spring.boot.repository.MemberRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member register(MemberRegisterRequestDto registerRequest) {
        memberRepository.findByEmail(registerRequest.getEmail())
                .ifPresent(find -> {throw new DuplicatedEmailException(registerRequest.getEmail());});

        Member member = Member.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .name(registerRequest.getName())
                .build();

        return memberRepository.save(member);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> login(String email, String password) {
        return findByEmail(email)
                .map(findMember ->{
                    if(!passwordEncoder.matches(password, findMember.getPassword())){
                        throw new BadCredentialsException("invalid user");
                    }
                    return findMember;
                });
    }

    @Transactional
    public void updateProfileImage(Long id, String profileImagePath) {
        memberRepository.findById(id)
                .ifPresent(findMember->{
                    findMember.setProfileImagePath(profileImagePath);
                });
    }
}
