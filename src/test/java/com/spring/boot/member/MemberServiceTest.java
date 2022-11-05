package com.spring.boot.member;

import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.presentaion.dto.MemberRegisterRequestDto;
import com.spring.boot.member.domain.member.Member;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@ActiveProfiles("test")
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void 회원가입() {
        // given
        String email = "a@naver.com";
        String password = "1234";
        String name = "a";
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto(email, password, name);

        // when
        Member member = memberService.register(MemberRegisterRequestDto.toEntity(requestDto));

        // then
        assertThat(member, is(notNullValue()));
        assertThat(member.getEmail(), is(email));
    }

}