package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Comment;
import com.esgi.pa.domain.entities.Post;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.PostRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  public List<Post> findAll() {
    Sort sortByUserIdAsc = Sort.by(Sort.Direction.DESC, "id");
    List<Post> posts = postRepository.findAll(sortByUserIdAsc);
    List<Post> posts1 = new ArrayList<>();
    posts.forEach(el -> {
      sortCommentsByIdDescending(el.getComments());
      posts1.add(el);
    });
    return posts1;
  }

  public void sortCommentsByIdDescending(List<Comment> comments) {
    Comparator<Comment> idComparator = Comparator.comparingLong(Comment::getId);
    Collections.sort(comments, idComparator.reversed());
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

  public void share(Users author, Post post, String datepost)
    throws TechnicalFoundException {
    postRepository.save(
      Post
        .builder()
        .description(post.getDescription())
        .author(author)
        .imgPath(post.getImgPath())
        .datepost(datepost)
        .build()
    );
  }
}
