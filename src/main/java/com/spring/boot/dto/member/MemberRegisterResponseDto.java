package com.spring.boot.dto.member;

import com.spring.boot.domain.Member;

public class MemberRegisterResponseDto {
    private MemberDto member;

    public MemberRegisterResponseDto(MemberDto member){
        this.member = member;
    }

    public MemberDto getMember() {
        return member;
    }
}
