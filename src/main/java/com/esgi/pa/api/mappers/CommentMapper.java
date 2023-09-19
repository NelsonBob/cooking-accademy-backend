package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.comment.CommentGetResponse;
import com.esgi.pa.domain.entities.Comment;
import java.util.List;

public interface CommentMapper {
  static CommentGetResponse toCommentGetResponse(Comment comment) {
    return new CommentGetResponse(
      comment.getId(),
      comment.getDescription(),
      UserMapper.toGetUserResponse(comment.getAuthor())
    );
  }

  static List<CommentGetResponse> toCommentGetResponse(List<Comment> entities) {
    return entities
      .stream()
      .map(CommentMapper::toCommentGetResponse)
      .distinct()
      .toList();
  }
}
