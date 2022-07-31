package com.spring.boot.dto.member;

import com.spring.boot.domain.Member;

public class MemberRegisterResponseDto {
    private Member member;

    public MemberRegisterResponseDto(Member member){
        this.member = member;
    }
}
