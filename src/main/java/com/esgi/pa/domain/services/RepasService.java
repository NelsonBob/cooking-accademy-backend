package com.esgi.pa.domain.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Repas;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.RepasRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service de gestion des utilisateurs
 */
@Service
@RequiredArgsConstructor
public class RepasService {

  private final RepasRepository repasRepository;

  public Repas getById(Long id) throws TechnicalNotFoundException {
    return repasRepository
        .findById(id)
        .orElseThrow(() -> new TechnicalNotFoundException(
            HttpStatus.NOT_FOUND,
            "No repas found with following id : "));
  }

  public Repas create(
      Intern creator,
      String name,
      String description,
      String imgPath,
      Integer quantity,
      Float price) throws TechnicalFoundException {
    if (repasRepository.findByName(name).isEmpty()) {
      Repas saveAb = repasRepository.save(
          Repas
              .builder()
              .name(name)
              .description(description)
              .creator(creator)
              .status(quantity <= 0 ? Boolean.FALSE : Boolean.TRUE)
              .imgPath(imgPath)
              .price(price)
              .quantity(quantity)
              .build());
      return saveAb;
    } else {
      throw new TechnicalFoundException(
          "Un repas existe Déjà avec ce nom :" + name);
    }
  }

  public Repas update(
      Repas repas,
      String name,
      String description,
      String imgPath,
      Integer quantity,
      Float price) throws TechnicalFoundException {
    if (repasRepository.findByName(name).isEmpty() ||
        repasRepository.findByName(name).isPresent() &&
            repasRepository.findByName(name).get().getId() == repas.getId()) {
      repas.setName(name);
      repas.setDescription(description);
      repas.setPrice(price);
      repas.setQuantity(quantity);
      repas.setStatus(quantity <= 0 ? Boolean.FALSE : Boolean.TRUE);
      repas.setImgPath(imgPath);
      Repas saveAb = repasRepository.save(
          repas);
      return saveAb;
    } else {
      throw new TechnicalFoundException(
          "Un repas existe Déjà avec ce nom :" + name);
    }
  }

  public void delete(Repas repas) {
    repasRepository.delete(repas);
  }

  public List<Repas> findAll() {
    return repasRepository.findAll();
  }

  public List<Repas> findByStatus() {
    return repasRepository.findByStatusOrderById(Boolean.TRUE);
  }
}
