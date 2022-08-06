package com.spring.boot.dto.post;

import com.spring.boot.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PostDto {
    private String title;
    private String body;
    private LocalDateTime createdDate;
    private List<PostImageDto> images;

    public static PostDto from(Post post){
        return PostDto.builder()
                .title(post.getTitle())
                .body(post.getBody())
                .createdDate(post.getCreatedDate())
                .images(post.getImages()
                        .stream()
                        .map(PostImageDto::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
