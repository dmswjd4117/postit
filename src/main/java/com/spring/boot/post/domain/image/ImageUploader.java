package com.spring.boot.post.domain.image;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.spring.boot.post.infrastructure.image.UploadFile;
import java.io.InputStream;
import java.util.Map;

public interface ImageUploader {

  String upload(UploadFile uploadFile);

  String upload(InputStream inputStream, long bytesLength, String fileName, String contentType,
      Map<String, String> metaData);

  String putObjectRequest(PutObjectRequest putObjectRequest);
}
