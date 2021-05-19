package io.cuongquocdang.blog.repository;

import io.cuongquocdang.blog.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findByPostId(Long postId, Pageable pageable);

    Optional<CommentEntity> findByIdAndPostId(Long id, Long postId);
}
