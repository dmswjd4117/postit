package com.spring.boot.service;

import com.spring.boot.aws.S3Client;
import com.spring.boot.domain.image.Image;
import com.spring.boot.domain.post.Post;
import com.spring.boot.domain.post.PostTag;
import com.spring.boot.domain.post.Tag;
import com.spring.boot.repository.TagRepository;
import com.spring.boot.util.UploadFile;
import com.spring.boot.repository.ImageRepository;
import com.spring.boot.repository.MemberRepository;
import com.spring.boot.repository.PostRepository;
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
    public Post createPost(String title, String body, Long writerId, List<String> tagNames, List<MultipartFile> multipartFiles) {
        return memberRepository.findById(writerId)
                .map(writer->{
                    Post post = postRepository.save(new Post(title, body, writer));

                    List<Image> images = convertToImages(multipartFiles, post);

                    Set<PostTag> postTags = tagNames
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
                .map(postRepository::findByMemberWithImages)
                .orElseThrow(IllegalArgumentException::new);
    }
}

