package com.esgi.pa.domain.services;

import com.esgi.pa.api.dtos.requests.optionServiceAbonnement.CreateOptionServiceAbonnementRequest;
import com.esgi.pa.api.dtos.requests.optionServiceAbonnement.UpdateOptionServiceAbonnementRequest;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.OptionAbonnement;
import com.esgi.pa.domain.entities.OptionServiceAbonnement;
import com.esgi.pa.domain.entities.ServiceAbonnement;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.OptionAbonnementRepository;
import com.esgi.pa.server.repositories.OptionServiceAbonnementRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des utilisateurs
 */
@Service
@RequiredArgsConstructor
public class OptionAbonnementService {

  private final OptionAbonnementRepository optionAbonnementRepository;
  private final OptionServiceAbonnementRepository optionServiceAbonnementRepository;
  private final ServiceAbonnementService serviceAbonnementService;

  public OptionAbonnement getById(Long id) throws TechnicalNotFoundException {
    return optionAbonnementRepository
      .findById(id)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No option found with following id : "
        )
      );
  }

  public void create(
    Intern creator,
    String name,
    List<CreateOptionServiceAbonnementRequest> data
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    if (optionAbonnementRepository.findByName(name).isEmpty()) {
      OptionAbonnement saveop = optionAbonnementRepository.save(
        OptionAbonnement
          .builder()
          .name(name)
          .status(Boolean.TRUE)
          .creator(creator)
          .build()
      );

      data.forEach(el -> {
        try {
          ServiceAbonnement serviceAbonnement = serviceAbonnementService.getById(
            el.serviceAbonnement()
          );
          optionServiceAbonnementRepository.save(
            OptionServiceAbonnement
              .builder()
              .serviceAbonnement(serviceAbonnement)
              .icon(el.icon())
              .description(el.description())
              .isValueicon(el.isValueicon())
              .descriptionvalue(el.descriptionvalue())
              .optionAbonnement(saveop)
              .build()
          );
        } catch (TechnicalNotFoundException e) {
          e.printStackTrace();
        }
      });
    } else {
      throw new TechnicalFoundException(
        "Un option d'abonnement existe Déjà avec ce nom :" + name
      );
    }
  }

  public OptionAbonnement update(
    OptionAbonnement optionAbonnement,
    String name,
    Boolean status,
    List<UpdateOptionServiceAbonnementRequest> data
  ) throws TechnicalFoundException {
    if (
      optionAbonnementRepository.findByName(name).isEmpty() ||
      optionAbonnementRepository.findByName(name).isPresent() &&
      optionAbonnementRepository.findByName(name).get().getId() ==
      optionAbonnement.getId()
    ) {
      optionAbonnement.setName(name);
      optionAbonnement.setStatus(status);
      OptionAbonnement saveop = optionAbonnementRepository.save(
        optionAbonnement
      );

      data.forEach(el -> {
        try {
          ServiceAbonnement serviceAbonnement = serviceAbonnementService.getById(
            el.serviceAbonnement()
          );
          OptionServiceAbonnement optionServiceAbonnement = optionServiceAbonnementRepository
            .findById(el.id())
            .orElseThrow(() ->
              new TechnicalNotFoundException(
                HttpStatus.NOT_FOUND,
                "No option found with following id : "
              )
            );
          optionServiceAbonnement.setServiceAbonnement(serviceAbonnement);
          optionServiceAbonnement.setIcon(el.icon());
          optionServiceAbonnement.setDescription(el.description());
          optionServiceAbonnement.setIsValueicon(el.isValueicon());
          optionServiceAbonnement.setDescriptionvalue(el.descriptionvalue());
          optionServiceAbonnementRepository.save(optionServiceAbonnement);
        } catch (TechnicalNotFoundException e) {
          e.printStackTrace();
        }
      });
      return saveop;
    } else {
      throw new TechnicalFoundException(
        "Un option d'abonnement existe Déjà avec ce nom :" + name
      );
    }
  }

  public void delete(OptionAbonnement optionAbonnement) {
    optionAbonnementRepository.delete(optionAbonnement);
  }

  public List<OptionAbonnement> findAll() {
    return optionAbonnementRepository.findAll();
  }

  public List<OptionAbonnement> findByStatus() {
    return optionAbonnementRepository.findByStatus(Boolean.TRUE);
  }
}
