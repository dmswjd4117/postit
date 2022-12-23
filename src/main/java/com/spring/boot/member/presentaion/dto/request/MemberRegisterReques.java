package com.spring.boot.member.presentaion.dto.request;

import com.spring.boot.member.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Setter
@Getter
public class MemberRegisterReques {

  private String email;
  private String password;
  private String name;

  public MemberRegisterReques(String email, String password, String name) {
    this.email = email;
    this.password = password;
    this.name = name;
  }

  public static Member toEntity(MemberRegisterReques requestDto) {
    return new Member(requestDto.getEmail(), requestDto.getPassword(), requestDto.getName());
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("email", email)
        .append("password", "[password]")
        .append("name", name)
        .toString();
  }

}
