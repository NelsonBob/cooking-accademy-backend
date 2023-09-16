package com.esgi.pa.api.dtos.requests.event;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.domain.enums.StatusReservationEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UpdateEventRequest(
  @NotBlank(message = "status is required") StatusReservationEnum statusEvent,
  @NotNull(message = "id event is required") long id
) {}
