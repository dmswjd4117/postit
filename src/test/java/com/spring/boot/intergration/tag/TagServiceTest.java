package com.spring.boot.intergration.tag;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.spring.boot.common.config.InfrastructureTestConfiguration;
import com.spring.boot.tag.application.TagService;
import com.spring.boot.tag.domain.Tag;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(InfrastructureTestConfiguration.class)
class TagServiceTest {

  @Autowired
  private TagService tagService;

  @Test
  @DisplayName("tag 저장한다.")
  void save_tag(){
    List<String> names = Arrays.asList("A", "A", "B", "C");
    Set<Tag> tags = tagService.createOrGetTags(names);
    assertThat(tags.stream().map(Tag::getTagName).collect(Collectors.toSet()),
        Is.is(equalTo(new HashSet<>(names))));
  }
}