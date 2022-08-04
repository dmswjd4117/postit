package com.spring.boot.dto.post;

import com.spring.boot.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PostResultDto {
    private String title;
    private String body;
    private Long writerId;
    private LocalDateTime createdDate;
    private List<PostImageDto> images;

    public static PostResultDto from(Post post){
        return PostResultDto.builder()
                .title(post.getTitle())
                .body(post.getBody())
                .writerId(post.getId())
                .createdDate(post.getCreatedDate())
                .images(post.getImages().stream().map(PostImageDto::from).collect(Collectors.toList()))
                .build();
    }
}
