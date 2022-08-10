package com.spring.boot.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PostRequestDto {
    private String title;
    private String body;
    private Long writerId;
    private List<String> postTags = new ArrayList<>();
    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("title", title)
                .append("body", body)
                .append("writerId", writerId)
                .append("post_tags", String.join(",", postTags))
                .toString();
    }
}
