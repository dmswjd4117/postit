package com.spring.boot.util;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.spring.boot.post.domain.image.ImageUploader;
import com.spring.boot.post.infrastructure.image.UploadFile;
import java.io.InputStream;
import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class MockImageUploader implements ImageUploader {
  @Override
  public String upload(UploadFile uploadFile) {
    return null;
  }

  @Override
  public String upload(InputStream inputStream, long bytesLength, String fileName,
      String contentType, Map<String, String> metaData) {
    return null;
  }

  @Override
  public String putObjectRequest(PutObjectRequest putObjectRequest) {
    return null;
  }
}
