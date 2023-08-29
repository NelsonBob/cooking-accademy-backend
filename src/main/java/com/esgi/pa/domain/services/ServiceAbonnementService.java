package com.esgi.pa.domain.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.ServiceAbonnement;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.ServiceAbonnementRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service de gestion des utilisateurs
 */
@Service
@RequiredArgsConstructor
public class ServiceAbonnementService {

  private final ServiceAbonnementRepository serviceAbonnementRepository;

  public ServiceAbonnement getById(Long id) throws TechnicalNotFoundException {
    return serviceAbonnementRepository
      .findById(id)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No service found with following id : "
        )
      );
  }

  public ServiceAbonnement create(
    Intern creator,
    String name,
    String description,
    String imgPath
  ) throws TechnicalFoundException {
    if (serviceAbonnementRepository.findByName(name).isEmpty()) {
      ServiceAbonnement saveAb = serviceAbonnementRepository.save(
        ServiceAbonnement
          .builder()
          .name(name)
          .description(description)
          .creator(creator)
          .status(Boolean.TRUE)
          .imgPath(imgPath)
          .build()
      );
      return saveAb;
    } else {
      throw new TechnicalFoundException(
        "Un service d'abonnement existe Déjà avec ce nom :" + name
      );
    }
  }

  public ServiceAbonnement update(
    ServiceAbonnement serviceAbonnement,
    String name,
    String description,
    Boolean status,
    String imgPath

  ) throws TechnicalFoundException {
    if (
      serviceAbonnementRepository.findByName(name).isEmpty() ||
      serviceAbonnementRepository.findByName(name).isPresent() &&
      serviceAbonnementRepository.findByName(name).get().getId() ==
      serviceAbonnement.getId()
    ) {
      serviceAbonnement.setName(name);
      serviceAbonnement.setDescription(description);
      serviceAbonnement.setStatus(status);
      serviceAbonnement.setImgPath(imgPath);
      ServiceAbonnement saveAb = serviceAbonnementRepository.save(
        serviceAbonnement
      );
      return saveAb;
    } else {
      throw new TechnicalFoundException(
        "Un service d'abonnement existe Déjà avec ce nom :" + name
      );
    }
  }

  public void delete(ServiceAbonnement serviceAbonnement) {
    serviceAbonnementRepository.delete(serviceAbonnement);
  }

  public List<ServiceAbonnement> findAll() {
    return serviceAbonnementRepository.findAll();
  }

  public List<ServiceAbonnement> findByStatus() {
    return serviceAbonnementRepository.findByStatusOrderById(Boolean.TRUE);
  }
}
