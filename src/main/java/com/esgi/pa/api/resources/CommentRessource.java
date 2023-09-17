package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.requests.comment.CommentAddRequest;
import com.esgi.pa.domain.entities.Post;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.CommentService;
import com.esgi.pa.domain.services.PostService;
import com.esgi.pa.domain.services.UserService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Api(tags = "Comment API")
public class CommentRessource {

  private final CommentService commentService;
  private final UserService userService;
  private final PostService postService;

  @PostMapping(value = "{id}")
  @ResponseStatus(CREATED)
  public ResponseEntity<?> create(
    @Valid @RequestBody CommentAddRequest request,
    @PathVariable Long id
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    Post post = postService.getById(request.post());
    commentService.create(users, post, request.description());
    return ResponseEntity.status(CREATED).build();
  }
}
