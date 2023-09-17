package com.esgi.pa.domain.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Like;
import com.esgi.pa.domain.entities.Post;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.server.repositories.LikeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LikeService {

  private final LikeRepository likeRepository;

  public void createorRemove(Users author, Post post)
    throws TechnicalFoundException {
    Optional<Like> like = likeRepository.findByPostAndUser(post, author);
    if (like.isPresent()) likeRepository.delete(
      like.get()
    ); else likeRepository.save(
      Like.builder().post(post).user(author).build()
    );
  }
}
