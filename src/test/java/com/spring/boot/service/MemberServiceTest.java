package com.spring.boot.service;

import com.spring.boot.dto.member.MemberRegisterRequestDto;
import com.spring.boot.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void 회원가입(){
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto();
        requestDto.setEmail("a@naver.com");
        requestDto.setPassword("1234");
        requestDto.setName("a");

        Member member = memberService.register(requestDto);

        System.out.println(member);
    }
}