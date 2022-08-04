package com.spring.boot.domain;

import lombok.Builder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.ObjectUtils.*;

@Builder
public class UploadFile {

    private final String originalFileName;
    private final String contentType;
    private final byte[] bytes;

    public UploadFile(String originalFileName, String contentType, byte[] bytes) {
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.bytes = bytes;
    }

    private static boolean verify(MultipartFile file){
        if(file == null || file.getSize() == 0 || file.getOriginalFilename() == null){
            return false;
        }
        String contentType = file.getContentType();
        return isNotEmpty(contentType) && contentType.toLowerCase().startsWith("image");
    }

    public static Optional<UploadFile> toUploadFile(MultipartFile multipartFile){
        if(verify(multipartFile)){
            try {
                return Optional.of(
                        UploadFile.builder()
                                .originalFileName(multipartFile.getOriginalFilename())
                                .contentType(multipartFile.getContentType())
                                .bytes(multipartFile.getBytes())
                                .build()
                );
            } catch (IOException ignored) {
            }
        }
        return Optional.empty();
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(bytes);
    }

    public long getBytesLength(){
        return bytes.length;
    }

    public String getRandomName(String basePath, String defaultExtension){
        String name = String.valueOf(UUID.randomUUID());
        if(!isEmpty(basePath)) {
            name = basePath + "/" + name;
        }
        return name + "." + getExtension(defaultExtension);
    }

    private String getExtension(String defaultExtension) {
        return defaultIfNull(FilenameUtils.getExtension(originalFileName), defaultExtension);
    }

}
