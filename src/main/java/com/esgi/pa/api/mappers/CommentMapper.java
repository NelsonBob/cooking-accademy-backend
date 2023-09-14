package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.requests.comment.CommentAddRequest;
import com.esgi.pa.api.dtos.responses.comment.CommentGetResponse;
import com.esgi.pa.domain.entities.Comment;

import java.util.List;

public interface CommentMapper {
    static CommentGetResponse toCommentGetResponse(Comment comment){
        return new CommentGetResponse(
                comment.getId(),
                comment.getPost().getId(),
                comment.getUser().getId(),
                comment.getUser().getName(),
                comment.getDescription()
        );
    }

     default List<CommentGetResponse> toCommentGetResponse(List<Comment> comments){
        return comments
                .stream()
                .map(CommentMapper::toCommentGetResponse)
                .distinct()
                .toList();
    }

    Comment addRequestToComment(CommentAddRequest commentAddRequest);
}
