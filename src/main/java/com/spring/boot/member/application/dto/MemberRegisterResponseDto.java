package com.spring.boot.member.application.dto;

public class MemberRegisterResponseDto {
    private MemberDto member;

    public MemberRegisterResponseDto(MemberDto member){
        this.member = member;
    }

    public MemberDto getMember() {
        return member;
    }
}
