package com.spring.boot.member.presentaion;

import com.spring.boot.common.dto.ApiResult;
import com.spring.boot.connection.application.dto.ConnectionDto;
import com.spring.boot.member.application.dto.MemberDto;
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
    private ApiResult<List<MemberDto>> getFollowingList(
            @PathVariable Long memberId
    ){
        return ApiResult.success(
                connectionService.getFollowing(memberId)
                        .stream()
                        .map(MemberDto::from)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/followers/{memberId}")
    private ApiResult<List<MemberDto>> getFollowerList(
            @PathVariable Long memberId
    ){
        return ApiResult.success(
                connectionService.getFollowers(memberId)
                        .stream()
                        .map(MemberDto::from)
                        .collect(Collectors.toList())
        );
    }
}
