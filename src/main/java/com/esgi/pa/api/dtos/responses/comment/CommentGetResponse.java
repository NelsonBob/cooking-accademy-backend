package com.esgi.pa.api.dtos.responses.comment;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record CommentGetResponse(
  Long id,
  String description,
  GetUserResponse author
) {}
