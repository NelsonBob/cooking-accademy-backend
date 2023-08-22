package com.esgi.pa.domain.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Salle;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.SalleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalleService {

  private final SalleRepository salleRepository;

  public Salle getById(Long id) throws TechnicalNotFoundException {
    return salleRepository
      .findById(id)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No salle found with following id : "
        )
      );
  }

  public Salle create(
    Intern creator,
    String name,
    String description,
    String imgPath,
    String gallerie
  ) throws TechnicalFoundException {
    if (salleRepository.findByName(name).isEmpty()) {
      Salle saveAb = salleRepository.save(
        Salle
          .builder()
          .name(name)
          .description(description)
          .creator(creator)
          .status(Boolean.TRUE)
          .imgPath(imgPath)
          .gallerie(gallerie)
          .build()
      );
      return saveAb;
    } else {
      throw new TechnicalFoundException(
        "Une salle existe Déjà avec ce nom :" + name
      );
    }
  }

  public Salle update(
    Salle salle,
    String name,
    String description,
    String imgPath,
    String gallerie,
    Boolean status
  ) throws TechnicalFoundException {
    if (
      salleRepository.findByName(name).isEmpty() ||
      salleRepository.findByName(name).isPresent() &&
      salleRepository.findByName(name).get().getId() == salle.getId()
    ) {
      salle.setName(name);
      salle.setDescription(description);
      salle.setStatus(status);
      salle.setImgPath(imgPath);
      salle.setGallerie(gallerie);
      Salle saveAb = salleRepository.save(salle);
      return saveAb;
    } else {
      throw new TechnicalFoundException(
        "Une salle existe Déjà avec ce nom :" + name
      );
    }
  }

  public void delete(Salle salle) {
    salleRepository.delete(salle);
  }

  public List<Salle> findAll() {
    return salleRepository.findAll();
  }

  public List<Salle> findByStatus() {
    return salleRepository.findByStatus(Boolean.TRUE);
  }
}
