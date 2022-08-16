package com.spring.boot.tag.domain;

import com.spring.boot.post.domain.PostTag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id
    @Column(name = "tag_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String tagName;

    @OneToMany(mappedBy = "tag")
    private List<PostTag> postTags;

    public Tag(String tagName) {
        this.tagName = tagName;
    }
    public String getTagName() {
        return tagName;
    }

    private Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)){
            return false;
        }
        Tag that = (Tag) obj;
        return Objects.equals(tagName, that.getTagName());
    }
}
