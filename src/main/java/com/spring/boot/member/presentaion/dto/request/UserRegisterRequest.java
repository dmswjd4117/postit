package com.spring.boot.member.presentaion.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Setter
@Getter
public class UserRegisterRequest {

  @Email
  private String email;
  @NotBlank
  private String password;
  @NotBlank
  private String name;

  public UserRegisterRequest(String email, String password, String name) {
    this.email = email;
    this.password = password;
    this.name = name;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("email", email)
        .append("password", "[password]").append("name", name).toString();
  }

}
