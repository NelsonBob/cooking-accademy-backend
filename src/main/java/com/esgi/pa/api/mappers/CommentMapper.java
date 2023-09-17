package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.comment.CommentGetResponse;
import com.esgi.pa.domain.entities.Comment;

public interface CommentMapper {
  static CommentGetResponse toCommentGetResponse(Comment comment) {
    return new CommentGetResponse(comment.getId(), comment.getDescription());
  }

  static List<CommentGetResponse> toCommentGetResponse(List<Comment> entities) {
    return entities
      .stream()
      .map(CommentMapper::toCommentGetResponse)
      .distinct()
      .toList();
  }
}
