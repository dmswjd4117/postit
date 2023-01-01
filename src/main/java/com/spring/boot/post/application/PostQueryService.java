package com.spring.boot.post.application;

import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.domain.Member;
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

  private final MemberService memberService;
  private final PostRepository postRepository;

  public PostQueryService(MemberService memberService,
      PostRepository postRepository) {
    this.memberService = memberService;
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
    Member member = memberService.findByMemberId(memberId);
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
