package com.esgi.pa.api.dtos.requests.stripe;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record ValidatePayementRequest(
  @NotNull(message = "Amount is required") Long amount, 
   @NotNull(message = "Amount is required") String token,

  @NotNull(message = "Item is required") List<ItemsRequest> items
) {}
