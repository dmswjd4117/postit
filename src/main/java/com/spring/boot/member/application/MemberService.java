package com.spring.boot.member.application;

import com.spring.boot.common.exception.DuplicatedException;
import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.member.application.dto.MemberDtoMapper;
import com.spring.boot.member.application.dto.MemberInfoDto;
import com.spring.boot.member.domain.Member;
import com.spring.boot.member.domain.MemberRepository;
import com.spring.boot.member.domain.role.MemberRole;
import com.spring.boot.member.domain.role.MemberRoleRepository;
import com.spring.boot.member.domain.role.Role;
import com.spring.boot.member.domain.role.RoleName;
import com.spring.boot.member.domain.role.RoleRepository;
import java.util.Optional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final MemberRoleRepository memberRoleRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public MemberService(MemberRepository memberRepository, MemberRoleRepository memberRoleRepository,
      RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.memberRoleRepository = memberRoleRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public MemberInfoDto register(Member memberInfo, RoleName roleName) {
    Member member = saveMember(memberInfo);

    MemberRole memberRole = getMemberRole(roleName);
    memberRole.changeMember(member);
    memberRoleRepository.save(memberRole);

    return MemberDtoMapper.memberInfoDto(member);
  }

  private MemberRole getMemberRole(RoleName roleName) {
    Role role = roleRepository.findByRoleName(roleName.name())
        .orElse(roleRepository.save(new Role(RoleName.MEMBER.getValue(), "member role")));
    return new MemberRole(role);
  }

  private Member saveMember(Member memberInfo) {
    memberRepository.findByEmail(memberInfo.getEmail())
        .ifPresent(find -> {
          throw new DuplicatedException("email", memberInfo.getEmail());
        });

    Member member = new Member(
        memberInfo.getEmail(),
        passwordEncoder.encode(memberInfo.getPassword()),
        memberInfo.getName());
    return memberRepository.save(member);
  }

  public Optional<Member> findByEmail(String email) {
    return memberRepository.findByEmail(email);
  }

  public Optional<MemberInfoDto> login(String email, String password) {
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
