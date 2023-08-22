package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.optionServiceAbonnement.GetOptionServiceAbonnementResponse;
import com.esgi.pa.domain.entities.OptionServiceAbonnement;

public interface OptionServiceAbonnementMapper {
  static GetOptionServiceAbonnementResponse toGetOptionServiceAbonnementResponse(
    OptionServiceAbonnement optionServiceAbonnement
  ) {
    return new GetOptionServiceAbonnementResponse(
      optionServiceAbonnement.getId(),
      optionServiceAbonnement.getIcon(),
      optionServiceAbonnement.getDescription(),
      optionServiceAbonnement.getValueicon(),
      optionServiceAbonnement.getDescriptionvalue(),
      ServiceAbonnementMapper.toGetServiceAbonnementResponse(
        optionServiceAbonnement.getServiceAbonnement()
      )
    );
  }

  static List<GetOptionServiceAbonnementResponse> toGetOptionServiceAbonnementResponse(
    List<OptionServiceAbonnement> entities
  ) {
    return entities
      .stream()
      .map(OptionServiceAbonnementMapper::toGetOptionServiceAbonnementResponse)
      .distinct()
      .toList();
  }
}
