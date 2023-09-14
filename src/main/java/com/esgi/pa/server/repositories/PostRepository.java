package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser_IdOrderByIdDesc(Long userId);
    Post findPostById(Long postId);
    void deleteById(Long id);
}
