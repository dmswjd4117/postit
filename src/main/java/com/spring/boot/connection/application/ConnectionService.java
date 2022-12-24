package com.spring.boot.connection.application;

import com.spring.boot.common.exception.DuplicatedException;
import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.connection.domain.ConnectionRepository;
import com.spring.boot.connection.domain.Connection;
import com.spring.boot.member.application.dto.MemberDtoMapper;
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
      throw new IllegalArgumentException("same memberId and targetMemberId error");
    }

    return memberRepository.findById(memberId)
        .map(findMember -> memberRepository
            .findById(targetMemberId)
            .map(targetMember -> {
              connectionRepository.findByMemberAndTargetMember(findMember, targetMember)
                  .ifPresent(c -> {
                    throw new DuplicatedException(Connection.class, "member: " + memberId,
                        " target: " + targetMemberId);
                  });
              Connection connection = new Connection(findMember, targetMember);
              return connectionRepository.save(connection);
            })
            .orElseThrow(() -> new NotFoundException(Member.class, targetMemberId)))
        .orElseThrow(() -> new NotFoundException(Member.class, memberId));
  }

  @Transactional
  public List<com.spring.boot.member.application.dto.MemberInfoDto> getFollowing(Long memberId) {
    return memberRepository.findById(memberId)
        .map(findMember -> findMember.getFollowing()
            .stream()
            .map(Connection::getTargetMember)
            .map(MemberDtoMapper::memberInfoDto)
            .collect(Collectors.toList()))
        .orElseThrow(() -> new NotFoundException(Member.class, memberId));
  }

  @Transactional
  public List<com.spring.boot.member.application.dto.MemberInfoDto> getFollowers(Long memberId) {
    return memberRepository.findById(memberId)
        .map(findMember -> findMember.getFollowers()
            .stream()
            .map(Connection::getMember)
            .map(MemberDtoMapper::memberInfoDto)
            .collect(Collectors.toList()))
        .orElseThrow(() -> new NotFoundException(Member.class, memberId));
  }

  @Transactional
  public boolean checkMemberFollowsTargetMember(Long memberId, Long targetMemberId) {
    Member targetMember = memberRepository.findById(targetMemberId)
        .orElseThrow(() -> new NotFoundException(Member.class, targetMemberId));
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new NotFoundException(Member.class, memberId));
    return connectionRepository.findByMemberAndTargetMember(member, targetMember).isPresent();
  }
}
