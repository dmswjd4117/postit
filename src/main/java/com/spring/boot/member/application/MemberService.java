package com.spring.boot.member.application;

import com.spring.boot.common.exception.AuthenticationFailException;
import com.spring.boot.common.exception.DuplicatedException;
import com.spring.boot.common.exception.MemberNotFoundException;
import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.member.application.dto.MemberDto;
import com.spring.boot.member.domain.Member;
import com.spring.boot.member.domain.UserRepository;
import com.spring.boot.role.application.RoleService;
import com.spring.boot.role.domain.Role;
import com.spring.boot.role.domain.RoleName;
import java.util.Optional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

  private final UserRepository userRepository;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;

  public MemberService(UserRepository userRepository, RoleService roleService,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public MemberDto register(String name, String email, String password) {
    userRepository.findByEmail(email)
        .ifPresent(find -> {
          throw new DuplicatedException("email", email);
        });

    Member member = saveMember(name, email, password);

    return MemberDto.from(member);
  }

  private Member saveMember(String name, String email, String password) {
    Role role = roleService.getRole(RoleName.MEMBER);

    Member member = new Member(
        email,
        passwordEncoder.encode(password),
        name,
        role);

    return userRepository.save(member);
  }

  private Optional<Member> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public MemberDto login(String email, String rawPassword) {
    return findByEmail(email)
        .map(findMember -> {
          if (!findMember.checkPassword(rawPassword, passwordEncoder)) {
            throw new BadCredentialsException("password doesn't match");
          }
          return findMember;
        })
        .map(MemberDto::from)
        .orElseThrow(() -> new AuthenticationFailException("email is invalid"));
  }

  @Transactional
  public void updateProfileImage(Long id, String profileImagePath) {
    userRepository.findById(id)
        .ifPresent(findMember -> {
          findMember.setProfileImagePath(profileImagePath);
        });
  }

  public Member findByMemberId(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
  }

}
