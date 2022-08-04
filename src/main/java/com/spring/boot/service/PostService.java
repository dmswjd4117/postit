package com.spring.boot.service;

import com.spring.boot.aws.S3Client;
import com.spring.boot.domain.Image;
import com.spring.boot.domain.Post;
import com.spring.boot.domain.UploadFile;
import com.spring.boot.repository.ImageRepository;
import com.spring.boot.repository.MemberRepository;
import com.spring.boot.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final S3Client s3Uploader;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;

    public PostService(S3Client s3Uploader, PostRepository postRepository, MemberRepository memberRepository, ImageRepository imageRepository) {
        this.s3Uploader = s3Uploader;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.imageRepository = imageRepository;
    }

    public Post createPost(String title, String body, Long writerId, List<MultipartFile> multipartFiles) {
        return memberRepository.findById(writerId)
                .map(writer->{
                    Post post = postRepository.save(new Post(title, body, writer));

                    List<Image> images = multipartFiles
                            .stream()
                            .map(UploadFile::toUploadFile)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .map(uploadFile -> imageRepository.save(new Image(s3Uploader.upload(uploadFile), post)))
                            .collect(Collectors.toList());

                    post.setImages(images);
                    return post;
                })
                .orElseThrow(IllegalArgumentException::new);
    }
}
