package com.esgi.pa.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Comment;
import com.esgi.pa.domain.entities.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByIdDesc(Post post);
}
