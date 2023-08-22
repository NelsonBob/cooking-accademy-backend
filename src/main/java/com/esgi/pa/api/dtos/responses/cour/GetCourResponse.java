package com.esgi.pa.api.dtos.responses.cour;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.domain.entities.Intern;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetCourResponse(
  Long id,
  String name,
  String description,
  String imgPath,
  String videoLink,
  String contentCour,
  Boolean status,
  Intern creator
) {}
