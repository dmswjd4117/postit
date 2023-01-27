package com.spring.boot.member.application;

import com.spring.boot.exception.AuthenticationFailException;
import com.spring.boot.exception.DuplicatedException;
import com.spring.boot.exception.MemberNotFoundException;
import com.spring.boot.member.application.dto.MemberAuthResponseDto;
import com.spring.boot.member.application.dto.MemberResponseDto;
import com.spring.boot.member.domain.Member;
import com.spring.boot.member.domain.MemberRepository;
import com.spring.boot.role.application.RoleService;
import com.spring.boot.role.domain.Role;
import com.spring.boot.role.domain.RoleName;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;

  public MemberService(MemberRepository memberRepository, RoleService roleService,
      PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.roleService = roleService;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public MemberResponseDto register(String name, String email, String password) {
    memberRepository.findByEmail(email)
        .ifPresent(find -> {
          throw new DuplicatedException("email", email);
        });

    Member member = saveMember(name, email, password);

    return MemberResponseDto.from(member);
  }

  private Member saveMember(String name, String email, String password) {
    Role role = roleService.getRole(RoleName.MEMBER);

    Member member = new Member(
        email,
        passwordEncoder.encode(password),
        name,
        role);

    return memberRepository.save(member);
  }

  public MemberAuthResponseDto login(String email, String rawPassword) {
    return  memberRepository.findByEmailWithRole(email)
        .map(findMember -> {
          if (!findMember.checkPassword(rawPassword, passwordEncoder)) {
            throw new BadCredentialsException("password doesn't match");
          }
          return findMember;
        })
        .map(MemberAuthResponseDto::from)
        .orElseThrow(() -> new AuthenticationFailException("email is invalid"));
  }

  @Transactional
  public void updateProfileImage(Long id, String profileImagePath) {
    memberRepository.findById(id)
        .ifPresent(findMember -> {
          findMember.setProfileImagePath(profileImagePath);
        });
  }

  public Member findByMemberId(Long id) {
    return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
  }

}
