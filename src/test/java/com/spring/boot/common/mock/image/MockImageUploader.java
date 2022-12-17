package com.spring.boot.common.mock.image;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.spring.boot.image.domain.ImageUploader;
import com.spring.boot.image.domain.UploadFile;
import java.io.InputStream;
import java.util.Map;

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
