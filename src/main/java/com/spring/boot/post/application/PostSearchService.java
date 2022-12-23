package com.spring.boot.post.application;

import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.application.dto.PostDtoMapper;
import com.spring.boot.post.application.dto.PostInfoDto;
import com.spring.boot.post.domain.Post;
import com.spring.boot.post.domain.image.PostImage;
import com.spring.boot.post.domain.tag.PostTag;
import com.spring.boot.post.infrastructure.PostRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PostSearchService {

  private final MemberService memberService;
  private final PostRepository postRepository;

  public PostSearchService(MemberService memberService,
      PostRepository postRepository) {
    this.memberService = memberService;
    this.postRepository = postRepository;
  }

  @Transactional
  public Post getPostByPostId(Long postId) {
    return postRepository.findByPostId(postId).map(post -> {
          // todo
          for (PostImage postImage : post.getPostImages()) {
            postImage.getId();
            break;
          }
          for (PostTag postTag : post.getPostTags()) {
            postTag.getId();
            break;
          }
          return post;
        })
        .orElseThrow(() -> new NotFoundException(Post.class, "post", postId));
  }

  @Transactional
  public List<PostInfoDto> getPostByMemberId(Long memberId) {
    Member member = memberService.findById(memberId);
    List<Post> posts = postRepository.findAllByMember(member);
    return PostDtoMapper.postInfoDtos(posts);
  }

  @Transactional(readOnly = true)
  public List<Post> getAllFollowingsPost(Long memberId, Pageable pageable) {
    return null;
  }

  @Transactional
  public List<Post> getAllPost() {
    return postRepository.findAll();
  }

}
