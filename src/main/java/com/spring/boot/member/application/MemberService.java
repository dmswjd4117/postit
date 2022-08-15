package com.spring.boot.member.application;

import com.spring.boot.member.domain.role.MemberRole;
import com.spring.boot.member.domain.role.Role;
import com.spring.boot.member.application.dto.MemberRegisterRequestDto;
import com.spring.boot.member.domain.member.Member;
import com.spring.boot.common.error.DuplicatedException;
import com.spring.boot.common.error.NotFoundException;
import com.spring.boot.member.domain.member.MemberRepository;
import com.spring.boot.member.domain.role.MemberRoleRepository;
import com.spring.boot.member.domain.role.RoleRepository;
import com.spring.boot.common.util.RoleName;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, MemberRoleRepository memberRoleRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.memberRoleRepository = memberRoleRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Member register(MemberRegisterRequestDto registerRequest) {
        memberRepository.findByEmail(registerRequest.getEmail())
                .ifPresent(find -> {
                    throw new DuplicatedException("email", registerRequest.getEmail());
                });

        Member member = Member.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .name(registerRequest.getName())
                .build();

        roleRepository.findByRoleName(RoleName.MEMBER.getValue())
                .map(role -> memberRoleRepository.save(new MemberRole(member, role)))
                .orElseThrow(()->new NotFoundException(Role.class, RoleName.MEMBER.getValue()));

        return memberRepository.save(member);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> login(String email, String password) {
        return findByEmail(email)
                .map(findMember -> {
                    if (!passwordEncoder.matches(password, findMember.getPassword())) {
                        throw new BadCredentialsException("invalid user");
                    }
                    return findMember;
                });
    }

    @Transactional
    public void updateProfileImage(Long id, String profileImagePath) {
        memberRepository.findById(id)
                .ifPresent(findMember -> {
                    findMember.setProfileImagePath(profileImagePath);
                });
    }

}
