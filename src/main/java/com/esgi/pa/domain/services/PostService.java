package com.esgi.pa.domain.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Post;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.PostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  public List<Post> findAll() {
    Sort sortByUserIdAsc = Sort.by(Sort.Direction.DESC, "id");
    return postRepository.findAll(sortByUserIdAsc);
  }

  public void create(
    Users author,
    String description,
    String imgPath,
    String datepost
  ) throws TechnicalFoundException {
    postRepository.save(
      Post
        .builder()
        .description(description)
        .author(author)
        .imgPath(imgPath)
        .datepost(datepost)
        .build()
    );
  }

  public Post getById(Long id) throws TechnicalNotFoundException {
    return postRepository
      .findById(id)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No materiel found with following id : "
        )
      );
  }
}
