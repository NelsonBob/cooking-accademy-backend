package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.optionAbonnement.GetOptionAbonnementResponse;
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
      )
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
}
