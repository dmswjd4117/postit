package com.spring.boot.post.application;

import com.spring.boot.common.exception.NotFoundException;
import com.spring.boot.common.exception.PostAccessDeniedException;
import com.spring.boot.common.exception.PostNotFoundException;
import com.spring.boot.connection.application.ConnectionService;
import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.domain.Member;
import com.spring.boot.post.presentation.dto.response.HomeFeedPostResponse;
import com.spring.boot.post.application.dto.response.PostResponseDto;
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
  private final ConnectionService connectionService;


  public PostQueryService(MemberService memberService,
      PostRepository postRepository, TagService tagService, ConnectionService connectionService) {
    this.memberService = memberService;
    this.postRepository = postRepository;
    this.tagService = tagService;
    this.connectionService = connectionService;
  }

  public boolean checkMemberAccessAuth(Long postId, Long readerId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException(postId));
    if (!post.isPrivate()) {
      return true;
    }
    if (readerId == null) {
      return false;
    }
    Member writer = post.getWriter();
    Member member = memberService.findByMemberId(readerId);
    if (readerId.equals(writer.getId())) {
      return true;
    }
    return connectionService.isMemberFollowTarget(member, writer);
  }

  @Transactional(readOnly = true)
  public PostResponseDto getPostByPostId(Long postId, Long readerId) {
    if (!checkMemberAccessAuth(postId, readerId)) {
      throw new PostAccessDeniedException();
    }
    return PostResponseDto.from(
        postRepository.findByPostId(postId)
            .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다"))
    );
  }

  @Transactional(readOnly = true)
  public List<PostResponseDto> getPostByWriterId(Long writerId, Pageable pageable, Long readerId) {

    Member member = memberService.findByMemberId(writerId);
    List<Post> posts = postRepository.findByMemberId(member.getId(), pageable).stream()
        .filter(post -> checkMemberAccessAuth(post.getId(), readerId))
        .collect(Collectors.toList());
    return PostResponseDto.from(posts);
  }

  @Transactional(readOnly = true)
  public List<PostResponseDto> getFollowingsPost(Long memberId, Pageable pageable) {
    return postRepository.findFollowingsPost(memberId, pageable).stream()
        .map(PostResponseDto::from)
        .collect(Collectors.toList());
  }


  @Transactional(readOnly = true)
  public List<PostResponseDto> getPostByTags(List<String> tagNames, Pageable pageable, Long readerId) {
    Set<Tag> tags = tagService.searchTags(tagNames);
    return PostResponseDto.from(
        postRepository.findByTags(tags, pageable).stream()
            .filter(post -> checkMemberAccessAuth(post.getId(), readerId))
            .collect(Collectors.toList()));
  }

  @Transactional(readOnly = true)
  public List<PostResponseDto> getHomeFeedPost(Long memberId, int pageSize, Long postId) {
    return postRepository.getHomeFeedPost(memberId, pageSize, postId).stream()
        .map(PostResponseDto::from)
        .collect(Collectors.toList());
  }
}
