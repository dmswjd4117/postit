package com.spring.boot.like.presentation;

import com.spring.boot.security.FormAuthentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/like/post")
public class LikeController {

  @PostMapping("/{postId}")
  public void like(
      @AuthenticationPrincipal FormAuthentication formAuthentication,
      @PathVariable(name = "postId") Long postId) {
    System.out.println(formAuthentication);
    System.out.println(postId);
  }
}
