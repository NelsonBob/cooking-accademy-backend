package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.comment.CommentAddRequest;
import com.esgi.pa.api.dtos.responses.comment.CommentGetResponse;
import com.esgi.pa.api.mappers.CommentMapper;
import com.esgi.pa.domain.entities.Comment;
import com.esgi.pa.domain.entities.Post;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.CommentService;
import com.esgi.pa.domain.services.PostService;
import com.esgi.pa.domain.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Api(tags = "Comment API")
public class CommentRessource {
    private final CommentService commentService;
    private final UserService userService;

    private final PostService postService;

    @PostMapping(value =  "{id}")
    @ResponseStatus(CREATED)
    public CommentGetResponse create(@Valid @RequestBody CommentAddRequest request,
                                     @PathVariable Long id)throws TechnicalFoundException, TechnicalNotFoundException, JsonProcessingException {
        Users users = userService.getById(id);
        Post post = postService.getById(request.getPostId());
        var comment = new Comment(request.getDescription(), post, users);
        commentService.add(
                comment
        );
    return CommentMapper.toCommentGetResponse(commentService.getById(comment.getId()) ) ;
    }
}
