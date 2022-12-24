package com.spring.boot.member.application;

import com.spring.boot.common.exception.DuplicatedException;
import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.member.application.dto.MemberDtoMapper;
import com.spring.boot.member.application.dto.MemberInfoDto;
import com.spring.boot.member.domain.Member;
import com.spring.boot.member.domain.MemberRepository;
import com.spring.boot.role.application.RoleService;
import com.spring.boot.role.domain.RoleName;
import java.util.Optional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;

  public MemberService(MemberRepository memberRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.roleService = roleService;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public MemberInfoDto register(String name, String email, String password, RoleName roleName) {
    Member member = saveMember(name, email, password, roleName);
    member.initRole(roleService.getRole(roleName));
    return MemberDtoMapper.memberInfoDto(member);
  }

  private Member saveMember(String name, String email, String password, RoleName roleName) {
    memberRepository.findByEmail(email)
        .ifPresent(find -> {
          throw new DuplicatedException("email", email);
        });

    Member member = new Member(
        email,
        passwordEncoder.encode(password),
        name);
    return memberRepository.save(member);
  }

  public Optional<Member> findByEmail(String email) {
    return memberRepository.findByEmail(email);
  }

  public Optional<com.spring.boot.member.application.dto.MemberInfoDto> login(String email, String password) {
    return findByEmail(email)
        .map(findMember -> {
          if (!findMember.checkPassword(passwordEncoder)) {
            throw new BadCredentialsException("invalid user");
          }
          return findMember;
        })
        .map(MemberDtoMapper::memberInfoDto);
  }

  @Transactional
  public void updateProfileImage(Long id, String profileImagePath) {
    memberRepository.findById(id)
        .ifPresent(findMember -> {
          findMember.setProfileImagePath(profileImagePath);
        });
  }

  public Member findById(Long id) {
    return memberRepository.findById(id).orElseThrow(() -> new NotFoundException(Member.class, id));
  }

}
