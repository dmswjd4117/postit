package com.spring.boot.common.config;

import com.spring.boot.common.imageUploader.ImageUploader;
import com.spring.boot.common.mock.image.MockImageUploader;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class InfrastructureTestConfiguration {
  @Bean
  public ImageUploader imageUploader(){
    return new MockImageUploader();
  }
}
