package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    void deleteLikeById(Long id);
    List<Like> findAllByPost_Id(Long postId);
    List<Like> findAllByUser_Id(Long userId);
    Optional<Like> findByUser_IdAndPost_Id(Long userId, Long postId);
}