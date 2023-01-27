package com.spring.boot.exception;

public class LikeNotFoundException extends RuntimeException{
  public LikeNotFoundException(){
    super("좋아요가 존재하지 않습니다.");
  }
}
