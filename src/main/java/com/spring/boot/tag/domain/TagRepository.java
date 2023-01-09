package com.spring.boot.tag.domain;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagName(String tagName);

    @Query("select t from Tag t where t.tagName in :tagNames")
    Set<Tag> getTagsByTagNames(List<String> tagNames);
}
