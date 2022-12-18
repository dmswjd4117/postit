package com.spring.boot.member.application;

import com.spring.boot.common.error.DuplicatedException;
import com.spring.boot.common.error.NotFoundException;
import com.spring.boot.member.domain.role.RoleName;
import com.spring.boot.member.domain.member.Member;
import com.spring.boot.member.domain.member.MemberRepository;
import com.spring.boot.member.domain.role.MemberRole;
import com.spring.boot.member.domain.role.MemberRoleRepository;
import com.spring.boot.member.domain.role.Role;
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
  public Member register(Member memberInfo, RoleName roleName) {
    Member member = saveMember(memberInfo);

    MemberRole memberRole = getMemberRole(roleName);
    memberRole.changeMember(member);
    memberRoleRepository.save(memberRole);

    return member;
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

  public Member findById(Long id) {
    return memberRepository.findById(id).orElseThrow(()->new NotFoundException(Member.class, id));
  }

}
