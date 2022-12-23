package com.spring.boot.image.infrastructure;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.spring.boot.image.domain.ImageUploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.Map;

@Component
@Profile("!test")
public final class S3ImageUploader implements ImageUploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    public S3ImageUploader(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public String upload(UploadFile uploadFile) {
        String BASE_PATH = "images";
        String DEFAULT_EXTENSION = "jpeg";

        return upload(uploadFile.getInputStream(),
                uploadFile.getBytesLength(),
                uploadFile.getRandomName(BASE_PATH, DEFAULT_EXTENSION),
                uploadFile.getContentType(),
                null);
    }

    public String upload(InputStream inputStream, long bytesLength, String fileName, String contentType, Map<String, String> metaData){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(bytesLength);
        objectMetadata.setContentType(contentType);
        if(metaData != null && !metaData.isEmpty()){
            objectMetadata.setUserMetadata(metaData);
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata);
        return putObjectRequest(putObjectRequest);
    }

    public String putObjectRequest(PutObjectRequest putObjectRequest){
        amazonS3Client.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucketName, putObjectRequest.getKey()).toString();
    }

}