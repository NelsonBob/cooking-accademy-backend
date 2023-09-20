package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.optionServiceAbonnement.GetOptionServiceAbonnementResponse;
import com.esgi.pa.api.dtos.responses.optionServiceAbonnement.GetOptionServiceAbonnementUserResponse;
import com.esgi.pa.domain.entities.OptionServiceAbonnement;

public interface OptionServiceAbonnementMapper {
  static GetOptionServiceAbonnementResponse toGetOptionServiceAbonnementResponse(
    OptionServiceAbonnement optionServiceAbonnement
  ) {
    return new GetOptionServiceAbonnementResponse(
      optionServiceAbonnement.getId(),
      optionServiceAbonnement.getIcon(),
      optionServiceAbonnement.getDescription(),
      optionServiceAbonnement.getIsValueicon(),
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

  static GetOptionServiceAbonnementUserResponse toGetOptionServiceAbonnementUserResponse(
    OptionServiceAbonnement optionServiceAbonnement
  ) {
    return new GetOptionServiceAbonnementUserResponse(
      optionServiceAbonnement.getId(),
      optionServiceAbonnement.getIsValueicon(),
      optionServiceAbonnement.getDescriptionvalue(),
      OptionAbonnementMapper.toGetOptionAbonnementUserResponse(
        optionServiceAbonnement.getOptionAbonnement()
      )
    );
  }

  static List<GetOptionServiceAbonnementUserResponse> toGetOptionServiceAbonnementUserResponse(
    List<OptionServiceAbonnement> entities
  ) {
    return entities
      .stream()
      .map(
        OptionServiceAbonnementMapper::toGetOptionServiceAbonnementUserResponse
      )
      .distinct()
      .toList();
  }
}
