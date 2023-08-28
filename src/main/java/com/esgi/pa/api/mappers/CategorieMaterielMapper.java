package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.categorieMateriel.GetCategorieMaterielResponse;
import com.esgi.pa.domain.entities.CategorieMateriel;

/**
 * Contient les méthodes pour mapper les entités utilisateur du domain vers des
 * dtos
 */
public interface CategorieMaterielMapper {
  static GetCategorieMaterielResponse toGetCategorieMaterielResponse(
      CategorieMateriel categorieMateriel) {
    return new GetCategorieMaterielResponse(
        categorieMateriel.getId(),
        categorieMateriel.getName(),
        InternMapper.toGetInternResponse(categorieMateriel.getCreator()));
  }

  static List<GetCategorieMaterielResponse> toGetCategorieMaterielResponse(
      List<CategorieMateriel> entities) {
    return entities
        .stream()
        .map(CategorieMaterielMapper::toGetCategorieMaterielResponse)
        .distinct()
        .toList();
  }
}
