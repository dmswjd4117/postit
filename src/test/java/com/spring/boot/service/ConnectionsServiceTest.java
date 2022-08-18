package com.spring.boot.service;

import com.spring.boot.connection.application.ConnectionService;
import com.spring.boot.connection.domain.Connections;
import com.spring.boot.common.error.DuplicatedException;
import com.spring.boot.common.error.NotFoundException;
import com.spring.boot.member.domain.member.Member;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConnectionsServiceTest {

    @Autowired
    private ConnectionService connectionService;
    private Long memberId;
    private List<Long> targetMembers;

    @BeforeAll
    void setUp() {
        memberId = 1L;
        targetMembers = Arrays.asList(2L, 3L, 4L);
    }

    @Test
    @DisplayName("존재하지 않는 멤버를 팔로우하면 예외 발생")
    public void 멤버가_없을땐_친구신청_실패() {
        assertThatThrownBy(() -> {
            Long invalid_member_id = 0L;
            connectionService.follow(memberId, invalid_member_id);
        }).isInstanceOf(NotFoundException.class);
    }

    // 1번이 2, 3, 4번 팔로우
    @Order(1)
    @ParameterizedTest
    @ValueSource(longs = {2L, 3L, 4L})
    public void 친구신청(Long targetMemberId) {
        Connections connections = connectionService.follow(memberId, targetMemberId);

        assertThat(connections, is(notNullValue()));
        assertThat(connections.getMember().getId(), is(memberId));
        assertThat(connections.getTargetMember().getId(), is(targetMemberId));
    }

    // 3번이 2번에게 팔로우
    private void follow(){
        Long memberId = 3L;
        Long targetMemberId = 2L;
        Connections connections = connectionService.follow(memberId, targetMemberId);

        assertThat(connections, is(notNullValue()));
        assertThat(connections.getMember().getId(), is(memberId));
        assertThat(connections.getTargetMember().getId(), is(targetMemberId));
    }

    @Order(1)
    @Test
    public void 친구신청2() {
        follow();
    }

    @Order(2)
    @Test
    public void 친구신청_중복이면_예외발생() {
        assertThatThrownBy(this::follow)
                .isInstanceOf(DuplicatedException.class);
    }

    @Order(2)
    @Test
    public void 팔로잉_목록을_조회한다() {
        assertThat(connectionService.getFollowing(memberId)
                .stream()
                .map(Member::getId)
                .collect(Collectors.toList())
                ,containsInAnyOrder(2L, 3L, 4L)
        );


    }

    @Order(2)
    @Test
    public void 팔로워_목록을_조회한다() {
        assertThat(connectionService.getFollowers(2L).stream()
                .map(Member::getId)
                .collect(Collectors.toList()),
                containsInAnyOrder(1L, 3L)
        );
    }


    @Order(3)
    @Test
    public void 팔로워인지_확인한다() {
        assertThat(connectionService.isMemberFollower(3L, 2L), is(true));
        assertThat(connectionService.isMemberFollower(1L, 2L), is(true));
        assertThat(connectionService.isMemberFollower(4L, 2L), is(false));
    }
}