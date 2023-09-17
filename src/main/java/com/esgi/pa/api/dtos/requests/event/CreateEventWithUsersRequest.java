package com.esgi.pa.api.dtos.requests.event;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record CreateEventWithUsersRequest(
  @NotBlank(message = "title is required") String title,
  @NotBlank(message = "start is required") String start,
  @NotBlank(message = "end is required") String end,
  String description,
  List<GetUserResponse> users
) {}
