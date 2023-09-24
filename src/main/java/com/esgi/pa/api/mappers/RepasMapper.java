package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.repas.GetRepasResponse;
import com.esgi.pa.domain.entities.Repas;

public interface RepasMapper {
  static GetRepasResponse toGetRepasResponse(Repas repas, Integer avis) {
    return new GetRepasResponse(
      repas.getId(),
      repas.getName(),
      repas.getDescription(),
      repas.getImgPath(),
      repas.getQuantity(),
      repas.getPrice(),
      repas.getStatus(),
      0.0
    );
  }

  static List<GetRepasResponse> toGetRepasResponse(
    List<Repas> entities,
    Integer avis
  ) {
    return entities
      .stream()
      .map(post -> RepasMapper.toGetRepasResponse(post, avis))
      .distinct()
      .toList();
  }
}
