package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.repas.GetRepasResponse;
import com.esgi.pa.domain.entities.Repas;

public interface RepasMapper {
  static GetRepasResponse toGetRepasResponse(Repas repas) {
    return new GetRepasResponse(
        repas.getId(),
        repas.getName(),
        repas.getDescription(),
        repas.getImgPath(),
        repas.getQuantity(),
        repas.getPrice(),
        repas.getStatus());
  }

  static List<GetRepasResponse> toGetRepasResponse(List<Repas> entities) {
    return entities
        .stream()
        .map(RepasMapper::toGetRepasResponse)
        .distinct()
        .toList();
  }

}
