package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;

import com.esgi.pa.api.dtos.requests.post.PostAddRequest;
import com.esgi.pa.api.dtos.requests.post.PostShareRequest;
import com.esgi.pa.api.dtos.responses.post.PostGetResponse;
import com.esgi.pa.api.mappers.PostMapper;
import com.esgi.pa.domain.entities.Post;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.PostService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Api(tags = "Post API")
public class PostRessource {

  private final UserService userService;
  private final PostService postService;

  @GetMapping(value = "{id}/item/{idk}")
  public PostGetResponse getOnePost(
    @PathVariable Long id,
    @PathVariable Long idk
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    Post post = postService.getById(idk);
    return PostMapper.toPostGetResponse(post, users);
  }

  @PostMapping(value = "{id}")
  @ResponseStatus(CREATED)
  public ResponseEntity<?> create(
    @Valid @RequestBody PostAddRequest request,
    @PathVariable Long id
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    postService.create(
      users,
      request.description(),
      request.imgPath(),
      request.datepost()
    );
    return ResponseEntity.status(CREATED).build();
  }

  @GetMapping(value = "{id}")
  public List<PostGetResponse> publications(@PathVariable Long id)
    throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    return PostMapper.toPostGetResponse(postService.findAll(), users);
  }

  @PostMapping(value = "{id}/share")
  public ResponseEntity<?> share(
    @Valid @RequestBody PostShareRequest request,
    @PathVariable Long id
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    Post post = postService.getById(request.id());
    postService.share(users, post, request.datepost());
    return ResponseEntity.status(CREATED).build();
  }
}
