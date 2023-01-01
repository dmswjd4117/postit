package com.spring.boot.post.application;

import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.user.application.UserService;
import com.spring.boot.user.domain.Member;
import com.spring.boot.post.application.dto.PostInfoDto;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.infrastructure.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PostQueryService {

  private final UserService userService;
  private final PostRepository postRepository;

  public PostQueryService(UserService userService,
      PostRepository postRepository) {
    this.userService = userService;
    this.postRepository = postRepository;
  }

  @Transactional
  public PostInfoDto getPostByPostId(Long postId) {
    return postRepository.findByPostId(postId)
        .map(PostInfoDto::from)
        .orElseThrow(() -> new NotFoundException(Post.class, "post does not exist by id", postId));
  }

  @Transactional
  public List<PostInfoDto> getPostByMemberId(Long memberId, Pageable pageable) {
    Member member = userService.findById(memberId);
    List<Post> posts = postRepository.findAllByMemberId(member.getId(), pageable);
    return PostInfoDto.from(posts);
  }

  @Transactional(readOnly = true)
  public List<PostInfoDto> getAllFollowingsPost(Long memberId, Pageable pageable) {
    return postRepository.findAllFollowingsPost(memberId, pageable).stream()
        .map(PostInfoDto::from)
        .collect(Collectors.toList());
  }

  @Transactional
  public List<PostInfoDto> getAllPost(Pageable pageable) {
    return postRepository.findAll(pageable)
        .stream()
        .map(PostInfoDto::from)
        .collect(Collectors.toList());
  }

}
