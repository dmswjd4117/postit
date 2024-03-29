package com.spring.boot.connection.presentation;

import com.spring.boot.common.presentation.response.ApiResult;
import com.spring.boot.connection.application.ConnectionService;
import com.spring.boot.connection.presentation.dto.ConnectionDto;
import com.spring.boot.member.presentaion.dto.response.MemberResponse;
import com.spring.boot.security.FormAuthentication;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/connection")
public class ConnectionController {

  private final ConnectionService connectionService;

  public ConnectionController(ConnectionService connectionService) {
    this.connectionService = connectionService;
  }

  @PostMapping("/{targetMemberId}")
  private ApiResult<ConnectionDto> follow(
      @AuthenticationPrincipal FormAuthentication formAuthentication,
      @PathVariable Long targetMemberId
  ) {
    connectionService.follow(formAuthentication.id, targetMemberId);
    return null;
  }

  @GetMapping("/following/{memberId}")
  private ApiResult<List<MemberResponse>> getFollowingList(
      @PathVariable Long memberId
  ) {
    return ApiResult.success(
        connectionService.getFollowing(memberId)
            .stream()
            .map(MemberResponse::from)
            .collect(Collectors.toList())
    );
  }

  @GetMapping("/followers/{memberId}")
  private ApiResult<List<MemberResponse>> getFollowerList(
      @PathVariable Long memberId
  ) {
    return ApiResult.success(
        connectionService.getFollowers(memberId)
            .stream()
            .map(MemberResponse::from)
            .collect(Collectors.toList())
    );
  }
}
