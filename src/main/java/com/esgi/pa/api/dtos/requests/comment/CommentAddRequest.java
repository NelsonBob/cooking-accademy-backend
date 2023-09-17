package com.esgi.pa.api.dtos.requests.comment;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record CommentAddRequest(
  @NotNull(message = "Post is required") Long post,
  @NotBlank(message = "Description is required") String description
) {}
