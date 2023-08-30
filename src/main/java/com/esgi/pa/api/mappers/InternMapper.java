package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.intern.GetInternResponse;
import com.esgi.pa.domain.entities.Intern;

/**
 * Contient les méthodes pour mapper les entités utilisateur du domain vers des
 * dtos
 */
public interface InternMapper {
  static GetInternResponse toGetInternResponse(Intern intern) {
    return new GetInternResponse(
        intern.getUsers().getId(),
        intern.getUsers().getName(),
        intern.getUsers().getEmail(),
        intern.getUsers().getRole(),
        intern.getFonction(),
        intern.getUsers().getImgPath());
  }

  static List<GetInternResponse> toGetInternResponse(List<Intern> entities) {
    return entities
        .stream()
        .map(InternMapper::toGetInternResponse)
        .distinct()
        .toList();
  }
}
