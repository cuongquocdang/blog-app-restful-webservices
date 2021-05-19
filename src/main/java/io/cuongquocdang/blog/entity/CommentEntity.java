package io.cuongquocdang.blog.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseAuditEntity {

    private String title;

    private String description;

    @Lob
    private String content;
}
