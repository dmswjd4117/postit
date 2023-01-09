package com.spring.boot.post.infrastructure;

import com.spring.boot.post.domain.Post;
import com.spring.boot.tag.domain.Tag;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

}

