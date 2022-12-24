package com.spring.boot.comment.presentation.dto;

import com.spring.boot.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {
    private String comment;
    private CommentWriter commentWriter;
    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .comment(comment.getBody())
                .commentWriter(new CommentWriter(comment.getWriter().getId(), comment.getWriter().getProfileImagePath()))
                .build();
    }
    private static class CommentWriter{
        private Long id;
        private String profileImagePath;

        public CommentWriter(Long id, String profileImagePath) {
            this.id = id;
            this.profileImagePath = profileImagePath;
        }

        public Long getId() {
            return id;
        }

        public String getProfileImagePath() {
            return profileImagePath;
        }
    }
}
