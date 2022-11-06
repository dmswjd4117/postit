package com.spring.boot.member.presentaion.dto;

public class MemberRegisterResponseDto {
    private MemberResponse member;

    public MemberRegisterResponseDto(MemberResponse member){
        this.member = member;
    }

    public MemberResponse getMember() {
        return member;
    }
}
