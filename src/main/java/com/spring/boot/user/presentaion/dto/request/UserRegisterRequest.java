package com.spring.boot.user.presentaion.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Setter
@Getter
public class UserRegisterRequest {

  private String email;
  private String password;
  private String name;

  public UserRegisterRequest(String email, String password, String name) {
    this.email = email;
    this.password = password;
    this.name = name;
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
