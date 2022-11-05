package com.spring.boot;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.spring.boot.common.util.S3Client;
import com.spring.boot.common.util.UploadFile;
import java.io.InputStream;
import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class MockS3Client implements S3Client {
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
  public String putS3(PutObjectRequest putObjectRequest) {
    return null;
  }
}
