package com.esgi.pa.api.dtos.responses.optionServiceAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.api.dtos.responses.serviceAbonnement.GetServiceAbonnementResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetOptionServiceAbonnementResponse(
  Long id,
  Boolean icon,
  Boolean description,
  String valueicon,
  String descriptionvalue,
  GetServiceAbonnementResponse serviceAbonnement
) {}
