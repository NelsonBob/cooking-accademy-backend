package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Like;
import com.esgi.pa.domain.entities.Post;
import com.esgi.pa.domain.entities.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
  Optional<Like> findByPostAndUser(Post post, Users user);
}
