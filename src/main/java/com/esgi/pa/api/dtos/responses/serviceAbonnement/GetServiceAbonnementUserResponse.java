package com.esgi.pa.api.dtos.responses.serviceAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.api.dtos.responses.optionServiceAbonnement.GetOptionServiceAbonnementUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.List;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetServiceAbonnementUserResponse(
  Long id,
  String name,
  String description,
  String imgPath,
  List<GetOptionServiceAbonnementUserResponse> options
) {}
