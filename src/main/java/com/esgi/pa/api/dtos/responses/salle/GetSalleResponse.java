package com.esgi.pa.api.dtos.responses.salle;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.List;

import com.esgi.pa.api.dtos.responses.intern.GetInternResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetSalleResponse(
  Long id,
  String name,
  String description,
  String imgPath,
  String[] gallerie,
  GetInternResponse creator
) {}
