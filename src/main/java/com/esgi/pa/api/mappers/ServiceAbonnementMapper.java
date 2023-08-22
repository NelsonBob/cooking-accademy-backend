package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.serviceAbonnement.GetServiceAbonnementResponse;
import com.esgi.pa.domain.entities.ServiceAbonnement;

/**
 * Contient les méthodes pour mapper les entités utilisateur du domain vers des dtos
 */
public interface ServiceAbonnementMapper {

    static GetServiceAbonnementResponse toGetServiceAbonnementResponse(ServiceAbonnement serviceAbonnement) {
        return new GetServiceAbonnementResponse(
                serviceAbonnement.getId(),
                serviceAbonnement.getName(),
                serviceAbonnement.getDescription(),
                serviceAbonnement.getStatus());
    }
    static List<GetServiceAbonnementResponse> toGetServiceAbonnementResponse(List<ServiceAbonnement> entities) {
        return entities.stream()
            .map(ServiceAbonnementMapper::toGetServiceAbonnementResponse)
            .distinct()
            .toList();
    }

}
