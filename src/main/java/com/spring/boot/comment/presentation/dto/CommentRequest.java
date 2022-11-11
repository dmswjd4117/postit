package com.spring.boot.comment.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@NoArgsConstructor
public class CommentRequest {
    private String comment;
    private Long postId;

    public CommentRequest(String comment, Long postId) {
        this.comment = comment;
        this.postId = postId;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("comment", comment)
            .append("post id", postId)
            .toString();
    }
}
