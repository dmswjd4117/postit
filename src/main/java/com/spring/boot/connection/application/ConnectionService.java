package com.spring.boot.connection.application;

import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.connection.domain.ConnectionRepository;
import com.spring.boot.connection.domain.Connection;
import com.spring.boot.user.application.dto.UserDtoMapper;
import com.spring.boot.user.application.dto.UserInfoDto;
import com.spring.boot.user.domain.User;
import com.spring.boot.user.domain.UserRepository;
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

    User sourceUser =  userRepository.findById(memberId)
        .orElseThrow(()->new NotFoundException(User.class,"member doesn't exist with id", memberId));
    User targetUser = userRepository.findById(targetMemberId)
        .orElseThrow(()->new NotFoundException(User.class,"member doesn't exist with id", targetMemberId));

    Connection connection = new Connection(sourceUser, targetUser);
    connectionRepository.save(connection);
    return connection;
  }

  @Transactional
  public List<UserInfoDto> getFollowing(Long memberId) {
    return userRepository.findById(memberId)
        .map(findMember -> findMember.getFollowing()
            .stream()
            .map(Connection::getTargetUser)
            .map(UserDtoMapper::memberInfoDto)
            .collect(Collectors.toList()))
        .orElseThrow(() -> new NotFoundException(User.class, memberId));
  }

  @Transactional
  public List<UserInfoDto> getFollowers(Long memberId) {
    return userRepository.findById(memberId)
        .map(findMember -> findMember.getFollowers()
            .stream()
            .map(Connection::getUser)
            .map(UserDtoMapper::memberInfoDto)
            .collect(Collectors.toList()))
        .orElseThrow(() -> new NotFoundException(User.class, memberId));
  }

  @Transactional
  public boolean checkMemberFollowsTargetMember(Long memberId, Long targetMemberId) {
    User targetUser = userRepository.findById(targetMemberId)
        .orElseThrow(() -> new NotFoundException(User.class, targetMemberId));
    User user = userRepository.findById(memberId)
        .orElseThrow(() -> new NotFoundException(User.class, memberId));
    return connectionRepository.findByMemberAndTargetMember(user, targetUser).isPresent();
  }
}
