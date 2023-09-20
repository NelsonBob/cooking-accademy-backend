package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.optionAbonnement.GetOptionAbonnementResponse;
import com.esgi.pa.api.dtos.responses.optionAbonnement.GetOptionAbonnementUserResponse;
import com.esgi.pa.domain.entities.OptionAbonnement;
import java.util.List;

/**
 * Contient les méthodes pour mapper les entités utilisateur du domain vers des dtos
 */
public interface OptionAbonnementMapper {
  static GetOptionAbonnementResponse toGetOptionAbonnementResponse(
    OptionAbonnement optionAbonnement
  ) {
    return new GetOptionAbonnementResponse(
      optionAbonnement.getId(),
      optionAbonnement.getName(),
      OptionServiceAbonnementMapper.toGetOptionServiceAbonnementResponse(
        optionAbonnement.getOptionServiceAbonnement()
      ),
      optionAbonnement.getStatus()
    );
  }

  static List<GetOptionAbonnementResponse> toGetOptionAbonnementResponse(
    List<OptionAbonnement> entities
  ) {
    return entities
      .stream()
      .map(OptionAbonnementMapper::toGetOptionAbonnementResponse)
      .distinct()
      .toList();
  }

  static GetOptionAbonnementUserResponse toGetOptionAbonnementUserResponse(
    OptionAbonnement optionAbonnement
  ) {
    return new GetOptionAbonnementUserResponse(
      optionAbonnement.getId(),
      optionAbonnement.getName(),
      optionAbonnement.getStatus()
    );
  }

  static List<GetOptionAbonnementUserResponse> toGetOptionAbonnementUserResponse(
    List<OptionAbonnement> entities
  ) {
    return entities
      .stream()
      .map(OptionAbonnementMapper::toGetOptionAbonnementUserResponse)
      .distinct()
      .toList();
  }
}
