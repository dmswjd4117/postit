package com.spring.boot.controller.post;

import com.spring.boot.controller.ApiResult;
import com.spring.boot.domain.post.Post;
import com.spring.boot.dto.post.PostRequestDto;
import com.spring.boot.dto.post.PostDto;
import com.spring.boot.security.FormAuthentication;
import com.spring.boot.service.PostService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResult<PostDto> createPost(
            @AuthenticationPrincipal FormAuthentication authentication,
            @ModelAttribute PostRequestDto postRequestDto,
            @RequestPart(required = false, name = "image") List<MultipartFile> multipartFiles
    ) {

        Post post = postService.createPost(
                postRequestDto.getTitle(),
                postRequestDto.getBody(),
                authentication.id,
                postRequestDto.getPostTags(),
                multipartFiles);

        return ApiResult.success(PostDto.from(post));
    }

    @GetMapping("/member/{memberId}")
    public ApiResult<List<PostDto>> getPost(
            @PathVariable Long memberId
    ){
        return ApiResult.success(
                postService.getPostByMemberId(memberId)
                        .stream()
                        .map(PostDto::from)
                        .collect(Collectors.toList())
        );
    }

}








