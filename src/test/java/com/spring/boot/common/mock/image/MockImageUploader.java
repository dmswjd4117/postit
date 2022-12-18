package com.spring.boot.common.mock.image;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.spring.boot.common.imageUploader.ImageUploader;
import com.spring.boot.common.imageUploader.UploadFile;
import java.io.InputStream;
import java.util.Map;

public class MockImageUploader implements ImageUploader {
  @Override
  public String upload(UploadFile uploadFile) {
    return "test image url";
  }

  @Override
  public String upload(InputStream inputStream, long bytesLength, String fileName,
      String contentType, Map<String, String> metaData) {
    return "test image url";
  }

  @Override
  public String putObjectRequest(PutObjectRequest putObjectRequest) {
    return null;
  }
}
