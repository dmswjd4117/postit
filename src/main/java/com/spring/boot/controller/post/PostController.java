package com.spring.boot.controller.post;

import com.spring.boot.controller.ApiResult;
import com.spring.boot.domain.Post;
import com.spring.boot.dto.post.PostRequestDto;
import com.spring.boot.dto.post.PostResultDto;
import com.spring.boot.security.FormAuthentication;
import com.spring.boot.service.PostService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResult<PostResultDto> post(
            @AuthenticationPrincipal FormAuthentication authentication,
            @ModelAttribute PostRequestDto postRequestDto,
            @RequestPart(required = false, name = "image") List<MultipartFile> multipartFiles
    ) {

        Post post = postService.createPost(
                postRequestDto.getTitle(),
                postRequestDto.getBody(),
                authentication.id,
                multipartFiles);

        return ApiResult.success(PostResultDto.from(post));
    }

}








