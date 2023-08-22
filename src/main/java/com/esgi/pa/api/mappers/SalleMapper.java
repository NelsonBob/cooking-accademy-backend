package com.esgi.pa.api.mappers;

import java.util.Arrays;
import java.util.List;

import com.esgi.pa.api.dtos.responses.salle.GetSalleResponse;
import com.esgi.pa.domain.entities.Salle;

/**
 * Contient les méthodes pour mapper les entités utilisateur du domain vers des dtos
 */
public interface SalleMapper {
  static GetSalleResponse toGetSalleResponse(Salle salle) {
    return new GetSalleResponse(
      salle.getId(),
      salle.getName(),
      salle.getDescription(),
      salle.getImgPath(),
      convertToEntityAttribute(salle.getGallerie()),
      InternMapper.toGetInternResponse(salle.getCreator())
    );
  }

  static List<GetSalleResponse> toGetSalleResponse(List<Salle> entities) {
    return entities
      .stream()
      .map(SalleMapper::toGetSalleResponse)
      .distinct()
      .toList();
  }

  static String[] convertToEntityAttribute(String dbData) {
    dbData = dbData.replaceAll("\\s+", ""); // Remove spaces

    // Remove square brackets
    if (dbData.startsWith("[") && dbData.endsWith("]")) {
      dbData = dbData.substring(1, dbData.length() - 1);
    }

    String[] elements = dbData.split(",");
    for (int i = 0; i < elements.length; i++) {
      elements[i] = elements[i].trim(); // Remove leading/trailing spaces
    }

    System.out.println(Arrays.toString(elements));
    return elements;
  }
}
