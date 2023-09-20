package com.esgi.pa.api.dtos.responses.optionAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetOptionAbonnementUserResponse(
  Long id,
  String name,
  Boolean status
) {}
