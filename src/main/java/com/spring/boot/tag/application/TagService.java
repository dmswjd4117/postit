package com.spring.boot.tag.application;

import com.spring.boot.tag.domain.Tag;
import com.spring.boot.tag.domain.TagRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TagService {

  private final TagRepository tagRepository;

  public TagService(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  public List<Tag> createOrGetTags(List<String> tagNames) {
    return tagNames
        .stream()
        .map(name -> tagRepository.findByTagName(name).orElseGet(()->tagRepository.save(new Tag(name))))
        .collect(Collectors.toList());
  }
}
