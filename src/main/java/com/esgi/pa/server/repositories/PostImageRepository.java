package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    Optional<PostImage> findPostImageByPost_Id(Long postId);
}
