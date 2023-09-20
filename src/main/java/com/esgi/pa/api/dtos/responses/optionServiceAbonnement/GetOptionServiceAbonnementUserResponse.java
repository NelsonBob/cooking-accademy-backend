package com.esgi.pa.api.dtos.responses.optionServiceAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.api.dtos.responses.optionAbonnement.GetOptionAbonnementUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetOptionServiceAbonnementUserResponse(
  Long id,
  Boolean isValueicon,
  String descriptionvalue,
  GetOptionAbonnementUserResponse optionAbonnement
) {}
