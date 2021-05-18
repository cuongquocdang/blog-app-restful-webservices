package io.cuongquocdang.blog.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Setter
@Getter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

}
