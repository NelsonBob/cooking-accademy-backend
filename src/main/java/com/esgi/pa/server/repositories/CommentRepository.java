package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteById(Long id);

    List<Comment> findAllByUser_Id(Long userId);

    List<Comment> findAllByPost_Id(Long postId);
}
