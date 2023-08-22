package com.esgi.pa.api.dtos.responses.optionAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.List;

import com.esgi.pa.api.dtos.responses.optionServiceAbonnement.GetOptionServiceAbonnementResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetOptionAbonnementResponse(
  Long id,
  String name,
  List<GetOptionServiceAbonnementResponse> optionServiceAbonnement
) {}
