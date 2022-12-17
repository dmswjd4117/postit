package com.spring.boot.image.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<PostImage, Long> {

}
