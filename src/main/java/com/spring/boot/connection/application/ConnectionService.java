package com.spring.boot.connection.application;

import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.connection.domain.ConnectionRepository;
import com.spring.boot.connection.domain.Connection;
import com.spring.boot.member.application.dto.MemberDto;
import com.spring.boot.member.domain.Member;
import com.spring.boot.member.domain.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConnectionService {

  private final UserRepository userRepository;
  private final ConnectionRepository connectionRepository;

  public ConnectionService(UserRepository userRepository,
      ConnectionRepository connectionRepository) {
    this.userRepository = userRepository;
    this.connectionRepository = connectionRepository;
  }

  @Transactional
  public Connection follow(Long memberId, Long targetMemberId) {

    if (memberId.equals(targetMemberId)) {
      throw new IllegalArgumentException("same memberId and targetMemberId error");
    }

    Member sourceMember =  userRepository.findById(memberId)
        .orElseThrow(()->new NotFoundException(Member.class,"member doesn't exist with id", memberId));
    Member targetMember = userRepository.findById(targetMemberId)
        .orElseThrow(()->new NotFoundException(Member.class,"member doesn't exist with id", targetMemberId));

    Connection connection = new Connection(sourceMember, targetMember);
    connectionRepository.save(connection);
    return connection;
  }

  @Transactional
  public List<MemberDto> getFollowing(Long memberId) {
    return userRepository.findById(memberId)
        .map(findMember -> findMember.getFollowing()
            .stream()
            .map(Connection::getTargetMember)
            .map(MemberDto::from)
            .collect(Collectors.toList()))
        .orElseThrow(() -> new NotFoundException(Member.class, memberId));
  }

  @Transactional
  public List<MemberDto> getFollowers(Long memberId) {
    return userRepository.findById(memberId)
        .map(findMember -> findMember.getFollowers()
            .stream()
            .map(Connection::getMember)
            .map(MemberDto::from)
            .collect(Collectors.toList()))
        .orElseThrow(() -> new NotFoundException(Member.class, memberId));
  }

  @Transactional
  public boolean isMemberFollowTarget(Long memberId, Long targetMemberId) {
    Member targetMember = userRepository.findById(targetMemberId)
        .orElseThrow(() -> new NotFoundException(Member.class, targetMemberId));
    Member member = userRepository.findById(memberId)
        .orElseThrow(() -> new NotFoundException(Member.class, memberId));
    return connectionRepository.findByMemberAndTargetMember(member, targetMember).isPresent();
  }

  @Transactional(readOnly = true)
  public boolean isMemberFollowTarget(Member member, Member targetMember) {
    return isMemberFollowTarget(member.getId(), targetMember.getId());
  }
}
