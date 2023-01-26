package com.spring.boot.connection.application;

import com.spring.boot.connection.domain.Connection;
import com.spring.boot.connection.domain.ConnectionRepository;
import com.spring.boot.exception.MemberNotFoundException;
import com.spring.boot.member.application.dto.MemberResponseDto;
import com.spring.boot.member.domain.Member;
import com.spring.boot.member.domain.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConnectionService {

  private final MemberRepository memberRepository;
  private final ConnectionRepository connectionRepository;

  public ConnectionService(MemberRepository memberRepository,
      ConnectionRepository connectionRepository) {
    this.memberRepository = memberRepository;
    this.connectionRepository = connectionRepository;
  }

  @Transactional
  public Connection follow(Long memberId, Long targetMemberId) {

    if (memberId.equals(targetMemberId)) {
      throw new IllegalArgumentException("소스멤버와 타겟멤버가 같습니다");
    }

    Member sourceMember = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException(memberId));
    Member targetMember = memberRepository.findById(targetMemberId)
        .orElseThrow(() -> new MemberNotFoundException(targetMemberId));

    Connection connection = new Connection(sourceMember, targetMember);
    connectionRepository.save(connection);

    return connection;
  }

  @Transactional
  public List<MemberResponseDto> getFollowing(Long memberId) {
    return memberRepository.findById(memberId)
        .map(findMember -> findMember.getFollowing()
            .stream()
            .map(Connection::getTargetMember)
            .map(MemberResponseDto::from)
            .collect(Collectors.toList()))
        .orElseThrow(() -> new MemberNotFoundException(memberId));
  }

  @Transactional
  public List<MemberResponseDto> getFollowers(Long memberId) {
    return memberRepository.findById(memberId)
        .map(findMember -> findMember.getFollowers()
            .stream()
            .map(Connection::getMember)
            .map(MemberResponseDto::from)
            .collect(Collectors.toList()))
        .orElseThrow(() -> new MemberNotFoundException(memberId));
  }

  @Transactional
  public boolean isMemberFollowTarget(Long memberId, Long targetMemberId) {
    Member targetMember = memberRepository.findById(targetMemberId)
        .orElseThrow(() -> new MemberNotFoundException(targetMemberId));
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException(memberId));
    return connectionRepository.findByMemberAndTargetMember(member, targetMember).isPresent();
  }

  @Transactional(readOnly = true)
  public boolean isMemberFollowTarget(Member member, Member targetMember) {
    return isMemberFollowTarget(member.getId(), targetMember.getId());
  }
}
