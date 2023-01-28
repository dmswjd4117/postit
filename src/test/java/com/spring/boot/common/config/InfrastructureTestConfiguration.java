package com.spring.boot.common.config;

import com.spring.boot.common.infrastructure.image.ImageUploader;
import com.spring.boot.common.api.MockImageUploader;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class InfrastructureTestConfiguration {
  @Bean
  public ImageUploader imageUploader(){
    return new MockImageUploader();
  }
}
