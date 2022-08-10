package com.spring.boot.service;

import com.spring.boot.dto.member.MemberRegisterRequestDto;
import com.spring.boot.domain.member.Member;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@TestPropertySource(properties = { "spring.config.location=classpath:application.yml,classpath:aws.yml" })
@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    private String email;
    private String password;
    private String name;

    @BeforeAll
    void setUp() {
        email = "a@naver.com";
        password = "1234";
        name = "a";
    }


    @Test
    public void 회원가입() {
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto();

        requestDto.setEmail(email);
        requestDto.setPassword(password);
        requestDto.setName(name);

        Member member = memberService.register(requestDto);

        assertThat(member, is(notNullValue()));
        assertThat(member.getEmail(), is(email));
    }

}