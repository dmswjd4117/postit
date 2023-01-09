package com.spring.boot.intergration.tag;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

import com.spring.boot.intergration.IntegrationTest;
import com.spring.boot.tag.application.TagService;
import com.spring.boot.tag.domain.Tag;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class TagServiceTest extends IntegrationTest {

  @Autowired
  private TagService tagService;

  @Nested
  class crud{
    @Test
    @DisplayName("태그 저장")
    void save_tag(){
      List<String> names = Arrays.asList("A", "A", "B", "C");
      Set<Tag> tags = tagService.saveTags(names);
      assertThat(tags.stream().map(Tag::getTagName).collect(Collectors.toSet()),
          Is.is(equalTo(new HashSet<>(names))));
    }
  }

  @Test
  @DisplayName("태그 검색")
  void search_tag(){
    List<String> names = Arrays.asList("A", "A", "B", "C");
    Set<Tag> tags = tagService.saveTags(names);

    assertThat(tagService.searchTags(names).size(), is(3));
  }
}












