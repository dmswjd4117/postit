package com.spring.boot.post.domain;

import com.spring.boot.common.domain.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image extends BaseTime {
    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Image(String imagePath, Post post){
        this.imagePath = imagePath;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("imagePath", imagePath)
                .append("created", getCreatedDate())
                .append("modified", getModifiedDate())
                .toString();
    }
}
