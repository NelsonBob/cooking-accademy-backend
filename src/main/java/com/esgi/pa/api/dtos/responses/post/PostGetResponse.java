package com.esgi.pa.api.dtos.responses.post;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.List;

import com.esgi.pa.api.dtos.responses.comment.CommentGetResponse;
import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record PostGetResponse(
  Long id,
  GetUserResponse author,
  String description,
  String imgPath,
  String datepost,
  List<CommentGetResponse> comments,
  Integer nbLikes,
  Boolean isLikes
) {}
