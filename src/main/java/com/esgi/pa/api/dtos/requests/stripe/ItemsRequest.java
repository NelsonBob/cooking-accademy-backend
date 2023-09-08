package com.esgi.pa.api.dtos.requests.stripe;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.domain.enums.TypeCommandeEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record ItemsRequest(
  @NotNull(message = "id is required") Long id,
  @NotBlank(message = "name is required") String name,
  @NotBlank(message = "price is required") Integer price,
  @NotBlank(message = "itemTotal is required") Integer itemTotal,
  @NotBlank(message = "quantity is required") Integer quantity,
  @NotBlank(message = "type is required") TypeCommandeEnum type
) {}
