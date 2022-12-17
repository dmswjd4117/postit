package com.spring.boot.image.domain;

import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.InputStream;
import java.util.Map;

public interface ImageUploader {

  String upload(UploadFile uploadFile);

  String upload(InputStream inputStream, long bytesLength, String fileName, String contentType,
      Map<String, String> metaData);

  String putObjectRequest(PutObjectRequest putObjectRequest);
}
