package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.serviceAbonnement.GetServiceAbonnementResponse;
import com.esgi.pa.api.dtos.responses.serviceAbonnement.GetServiceAbonnementUserResponse;
import com.esgi.pa.domain.entities.ServiceAbonnement;

public interface ServiceAbonnementMapper {
  static GetServiceAbonnementResponse toGetServiceAbonnementResponse(
    ServiceAbonnement serviceAbonnement
  ) {
    return new GetServiceAbonnementResponse(
      serviceAbonnement.getId(),
      serviceAbonnement.getName(),
      serviceAbonnement.getDescription(),
      serviceAbonnement.getImgPath(),
      serviceAbonnement.getStatus(),
      serviceAbonnement.getIsDefault()
    );
  }

  static List<GetServiceAbonnementResponse> toGetServiceAbonnementResponse(
    List<ServiceAbonnement> entities
  ) {
    return entities
      .stream()
      .map(ServiceAbonnementMapper::toGetServiceAbonnementResponse)
      .distinct()
      .toList();
  }

  static GetServiceAbonnementUserResponse toGetServiceAbonnementUserResponse(
    ServiceAbonnement serviceAbonnement
  ) {
    return new GetServiceAbonnementUserResponse(
      serviceAbonnement.getId(),
      serviceAbonnement.getName(),
      serviceAbonnement.getDescription(),
      serviceAbonnement.getImgPath(),
      OptionServiceAbonnementMapper.toGetOptionServiceAbonnementUserResponse(
        serviceAbonnement.getOptionServiceAbonnement()
      )
    );
  }

  static List<GetServiceAbonnementUserResponse> toGetServiceAbonnementUserResponse(
    List<ServiceAbonnement> entities
  ) {
    return entities
      .stream()
      .map(ServiceAbonnementMapper::toGetServiceAbonnementUserResponse)
      .distinct()
      .toList();
  }
}
