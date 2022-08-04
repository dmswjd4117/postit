package com.spring.boot.dto.post;

import com.spring.boot.domain.Image;
import lombok.Getter;

@Getter
public class PostImageDto {

    private Long id;
    private String imagePath;

    public PostImageDto(Long id, String imagePath) {
        this.id = id;
        this.imagePath = imagePath;
    }

    public static PostImageDto from(Image image) {
        return new PostImageDto(image.getId(), image.getImagePath());
    }
}
