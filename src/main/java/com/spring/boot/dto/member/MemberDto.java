package com.spring.boot.dto.member;

import com.spring.boot.domain.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
@Builder
@Getter
public class MemberDto {

    private Long id;

    private String email;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .createdDate(member.getCreatedDate())
                .modifiedDate(member.getModifiedDate())
                .build();
    }
}
