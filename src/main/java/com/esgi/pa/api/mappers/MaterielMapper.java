package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.materiel.GetMaterielResponse;
import com.esgi.pa.domain.entities.Materiel;
import java.util.List;

public interface MaterielMapper {
  static GetMaterielResponse toGetMaterielResponse(Materiel materiel) {
    return new GetMaterielResponse(
      materiel.getId(),
      materiel.getName(),
      materiel.getDescription(),
      materiel.getImgPath(),
      materiel.getStatus(),
      convertToEntityAttribute(materiel.getGallerie()),
      materiel.getQuantity(),
      materiel.getPrice(),
      CategorieMaterielMapper.toGetCategorieMaterielItemResponse(
        materiel.getCategorieMateriel()
      ),
      InternMapper.toGetInternResponse(materiel.getCreator()),
      0.0
    );
  }

  static List<GetMaterielResponse> toGetMaterielResponse(
    List<Materiel> entities
  ) {
    return entities
      .stream()
      .map(MaterielMapper::toGetMaterielResponse)
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
    return elements;
  }
}
