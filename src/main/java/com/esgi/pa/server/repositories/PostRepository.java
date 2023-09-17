package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}
