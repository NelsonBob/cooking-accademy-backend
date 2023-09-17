package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Comment;
import com.esgi.pa.domain.entities.Post;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.server.repositories.CommentRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;

  public List<Comment> findByPost(Post post) {
    return commentRepository.findByPostOrderByIdDesc(post);
  }

  public void create(Users author, Post post, String description)
    throws TechnicalFoundException {
    commentRepository.save(
      Comment
        .builder()
        .post(post)
        .author(author)
        .description(description)
        .build()
    );
  }
}
