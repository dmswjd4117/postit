package com.spring.boot.service;

import com.spring.boot.domain.Connections;
import com.spring.boot.error.DuplicatedException;
import com.spring.boot.error.NotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

@TestPropertySource(properties = { "spring.config.location=classpath:application.yml,classpath:aws.yml" })
@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConnectionServiceTest {

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
    @DisplayName("멤버가 없으면 조회실패")
    public void 멤버가_없을땐_친구신청_실패() {
        assertThatThrownBy(() -> {
            connectionService.follow(memberId, 0L);
        }).isInstanceOf(NotFoundException.class);
    }

    @Order(1)
    @ParameterizedTest
    @ValueSource(longs = {2L, 3L, 4L})
    public void 친구신청(Long targetMemberId) {
        Connections connections = connectionService.follow(memberId, targetMemberId);

        assertThat(connections, is(notNullValue()));
        assertThat(connections.getMember().getId(), is(memberId));
        assertThat(connections.getTargetMember().getId(), is(targetMemberId));
    }

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
        connectionService.getFollowings(memberId)
                .stream()
                .forEach(member -> {
                    System.out.println(member.getId());
                });
    }

    @Order(2)
    @Test
    public void 팔로워_목록을_조회한다() {
        connectionService.getFollowers(2L)
                .stream()
                .forEach(member -> {
                    System.out.println(member.getId());
                });
    }
}