package com.spring.boot.post.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostRequest {
    private String title;
    private String body;
    private List<String> postTags;
    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("title", title)
                .append("body", body)
                .append("post_tags", String.join(",", postTags))
                .toString();
    }
}