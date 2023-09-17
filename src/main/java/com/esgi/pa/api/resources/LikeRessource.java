package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.OK;

import com.esgi.pa.domain.entities.Post;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.LikeService;
import com.esgi.pa.domain.services.PostService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
@Api(tags = "Like API")
public class LikeRessource {

  private final LikeService likeService;
  private final UserService userService;
  private final PostService postService;

  @GetMapping(value = "{id}/post/{idk}")
  public ResponseEntity<?> createorRemove(
    @PathVariable Long id,
    @PathVariable Long idk
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    Post post = postService.getById(idk);
    likeService.createorRemove(users, post);
    return ResponseEntity.status(OK).build();
  }
}
