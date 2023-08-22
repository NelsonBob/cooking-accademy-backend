package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.cour.GetCourResponse;
import com.esgi.pa.domain.entities.Cour;
import java.util.List;

/**
 * Contient les méthodes pour mapper les entités utilisateur du domain vers des dtos
 */
public interface CourMapper {
  static GetCourResponse toGetCourResponse(Cour cour) {
    return new GetCourResponse(
      cour.getId(),
      cour.getName(),
      cour.getDescription(),
      cour.getImgPath(),
      cour.getVideoLink(),
      cour.getContentCour(),
      cour.getStatus(),
      cour.getCreator()
    );
  }

  static List<GetCourResponse> toGetCourResponse(List<Cour> entities) {
    return entities
      .stream()
      .map(CourMapper::toGetCourResponse)
      .distinct()
      .toList();
  }
}
