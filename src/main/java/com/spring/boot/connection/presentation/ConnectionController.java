package com.spring.boot.connection.presentation;

import com.spring.boot.common.dto.ApiResult;
import com.spring.boot.connection.presentation.dto.ConnectionDto;
import com.spring.boot.member.presentaion.dto.MemberResponse;
import com.spring.boot.security.FormAuthentication;
import com.spring.boot.connection.application.ConnectionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    ){
        connectionService.follow(formAuthentication.id, targetMemberId);
        return null;
    }

    @GetMapping("/following/{memberId}")
    private ApiResult<List<MemberResponse>> getFollowingList(
            @PathVariable Long memberId
    ){
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
    ){
        return ApiResult.success(
                connectionService.getFollowers(memberId)
                        .stream()
                        .map(MemberResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
