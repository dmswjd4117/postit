package com.spring.boot.post.application;

import com.spring.boot.common.util.S3Client;
import com.spring.boot.post.application.dto.PostRequestDto;
import com.spring.boot.post.domain.Image;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.domain.PostTag;
import com.spring.boot.tag.domain.Tag;
import com.spring.boot.common.error.NotFoundException;
import com.spring.boot.tag.domain.TagRepository;
import com.spring.boot.common.util.UploadFile;
import com.spring.boot.post.domain.ImageRepository;
import com.spring.boot.member.domain.member.MemberRepository;
import com.spring.boot.post.domain.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final S3Client s3Uploader;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;

    public PostService(S3Client s3Uploader, PostRepository postRepository, MemberRepository memberRepository, ImageRepository imageRepository, TagRepository tagRepository) {
        this.s3Uploader = s3Uploader;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.imageRepository = imageRepository;
        this.tagRepository = tagRepository;
    }


    @Transactional
    public Post createPost(Long writerId, PostRequestDto postRequestDto, List<MultipartFile> multipartFiles) {
        return memberRepository.findById(writerId)
                .map(writer->{
                    Post post = postRepository.save(new Post(postRequestDto.getTitle(), postRequestDto.getBody(), writer));

                    List<Image> images = convertToImages(multipartFiles, post);

                    Set<PostTag> postTags = postRequestDto.getPostTags()
                            .stream()
                            .distinct()
                            .map(tagName -> tagRepository.findByTagName(tagName)
                                    .orElse(new Tag(tagName)))
                            .map(tag -> new PostTag(tag, post))
                            .collect(Collectors.toSet());

                    post.setImages(images);
                    post.setPostTags(postTags);

                    return post;
                })
                .orElseThrow(IllegalArgumentException::new);
    }

    private List<Image> convertToImages(List<MultipartFile> multipartFiles, Post post) {
        return multipartFiles
                .stream()
                .map(UploadFile::toUploadFile)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(uploadFile -> new Image(s3Uploader.upload(uploadFile), post))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Post> getPostByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .map(postRepository::findByMemberWithTags)
                .map(posts -> {
                    posts.forEach(post -> {
                        post.getImages().forEach(Image::getId);
                    });
                    return posts;
                })
                .orElseThrow(()->new NotFoundException(Post.class, "member", memberId));
    }

    @Transactional
    public Post getPostByPostId(Long postId) {
        return postRepository.findByPostIdWithTags(postId)
                .map(post -> {
                    post.getImages().forEach(Image::getId);
                    return post;
                })
                .orElseThrow(()->new NotFoundException(Post.class, "post", postId));
    }
}

