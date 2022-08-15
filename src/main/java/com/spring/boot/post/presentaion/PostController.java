package com.spring.boot.post.presentaion;

import com.spring.boot.common.dto.ApiResult;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.application.dto.PostRequestDto;
import com.spring.boot.post.application.dto.PostDto;
import com.spring.boot.security.FormAuthentication;
import com.spring.boot.post.application.PostService;
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
    public ApiResult<List<PostDto>> getPostByMemberId(
            @PathVariable Long memberId
    ){
        return ApiResult.success(
                postService.getPostByMemberId(memberId)
                        .stream()
                        .map(PostDto::from)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{postId}")
    public ApiResult<PostDto> getPostByPostId(
            @PathVariable Long postId
    ){
        return ApiResult.success(
                PostDto.from(postService.getPostByPostId(postId))
        );
    }

}








