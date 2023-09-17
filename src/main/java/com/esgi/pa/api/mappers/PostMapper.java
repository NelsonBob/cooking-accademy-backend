package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.post.PostGetResponse;
import com.esgi.pa.domain.entities.Post;
import com.esgi.pa.domain.entities.Users;

public interface PostMapper {
  static PostGetResponse toPostGetResponse(Post post, Users user) {
    boolean userLiked = post
      .getLikes()
      .stream()
      .anyMatch(like -> like.getUser().equals(user));

    return new PostGetResponse(
      post.getId(),
      UserMapper.toGetUserResponse(post.getAuthor()),
      post.getDescription(),
      post.getImgPath(),
      post.getDatepost(),
      CommentMapper.toCommentGetResponse(post.getComments()),
      post.getLikes().size(),
      userLiked
    );
  }

  static List<PostGetResponse> toPostGetResponse(
    List<Post> entities,
    Users user
  ) {
    return entities
      .stream()
      .map(post -> PostMapper.toPostGetResponse(post, user))
      .distinct()
      .toList();
  }
}
