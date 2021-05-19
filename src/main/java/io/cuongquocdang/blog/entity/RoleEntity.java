package io.cuongquocdang.blog.entity;

import io.cuongquocdang.blog.enumeration.RoleName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Setter
@Getter
public class RoleEntity extends BaseAuditEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleName name;

    @Column(name = "description")
    private String description;

}
