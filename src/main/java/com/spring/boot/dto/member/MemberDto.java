package com.spring.boot.dto.member;

import com.spring.boot.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Builder
@Getter
public class MemberDto {

    private Long id;

    private String email;

    private String profileImagePath;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .profileImagePath(member.getProfileImagePath())
                .createdDate(member.getCreatedDate())
                .modifiedDate(member.getModifiedDate())
                .build();
    }
}
