package com.spring.boot.post.application;

import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.application.dto.response.PostDto;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.infrastructure.PostRepository;
import com.spring.boot.tag.application.TagService;
import com.spring.boot.tag.domain.Tag;
import java.util.List;
import java.util.Set;
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
  private final TagService tagService;

  public PostQueryService(MemberService memberService,
      PostRepository postRepository, TagService tagService) {
    this.memberService = memberService;
    this.postRepository = postRepository;
    this.tagService = tagService;
  }


  @Transactional(readOnly = true)
  public PostDto getPostByPostId(Long postId) {
    return postRepository.findByPostId(postId)
        .map(PostDto::from)
        .orElseThrow(() -> new NotFoundException(Post.class, "not found with post id", postId));
  }

  @Transactional(readOnly = true)
  public List<PostDto> getPostByMemberId(Long memberId, Pageable pageable) {
    Member member = memberService.findByMemberId(memberId);
    List<Post> posts = postRepository.findAllByMemberId(member.getId(), pageable);
    return PostDto.from(posts);
  }

  @Transactional(readOnly = true)
  public List<PostDto> getFollowingsPost(Long memberId, Pageable pageable) {
    return postRepository.findAllFollowingsPost(memberId, pageable).stream()
        .map(PostDto::from)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<PostDto> getPost(Pageable pageable) {
    return postRepository.findAll(pageable)
        .stream()
        .map(PostDto::from)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<PostDto> getPostByTags(List<String> tagNames, Pageable pageable) {
    Set<Tag> tags = tagService.searchTags(tagNames);
    return PostDto.from(postRepository.searchByTags(tags, pageable));
  }
}
