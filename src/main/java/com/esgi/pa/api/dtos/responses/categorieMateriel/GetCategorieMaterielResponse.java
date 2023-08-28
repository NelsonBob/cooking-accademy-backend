package com.esgi.pa.api.dtos.responses.categorieMateriel;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.api.dtos.responses.intern.GetInternResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetCategorieMaterielResponse(
  Long id,
  String name,
  GetInternResponse creator
) {}
