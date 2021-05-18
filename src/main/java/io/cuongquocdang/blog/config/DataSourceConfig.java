package io.cuongquocdang.blog.config;

import io.cuongquocdang.blog.entity.RoleEntity;
import io.cuongquocdang.blog.enumeration.RoleName;
import io.cuongquocdang.blog.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void initData() {
        createRole();
    }

    private void createRole() {
        RoleEntity roleAdmin = new RoleEntity();
        roleAdmin.setName(RoleName.ADMIN);
        roleAdmin.setDescription("Admin Permission");
        roleRepository.save(roleAdmin);
        RoleEntity roleUser = new RoleEntity();
        roleUser.setName(RoleName.USER);
        roleUser.setDescription("User Permission");
        roleRepository.save(roleUser);
    }
}
