package com.spring.boot.tag.domain;

import com.spring.boot.tag.application.dto.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagName(String tagName);
}
