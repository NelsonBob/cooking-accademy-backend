package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Cour;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.CourRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des utilisateurs
 */
@Service
@RequiredArgsConstructor
public class CourService {

  private final CourRepository courRepository;

  public Cour getById(Long id) throws TechnicalNotFoundException {
    return courRepository
        .findById(id)
        .orElseThrow(() -> new TechnicalNotFoundException(
            HttpStatus.NOT_FOUND,
            "No cour found with following id : "));
  }

  public void create(
      Intern creator,
      String name,
      String description,
      String imgPath,
      String videoLink,
      String contentCour,
      Boolean isVideoLocal) throws TechnicalFoundException {
    courRepository.save(
        Cour
            .builder()
            .name(name)
            .description(description)
            .creator(creator)
            .status(Boolean.TRUE)
            .imgPath(imgPath)
            .contentCour(contentCour)
            .videoLink(videoLink)
            .isVideoLocal(isVideoLocal)
            .build());
  }

  public Cour update(
      Cour cour,
      String name,
      String description,
      String imgPath,
      String videoLink,
      String contentCour,
      Boolean status,
      Boolean isVideoLocal) {
    cour.setName(name);
    cour.setDescription(description);
    cour.setStatus(status);
    cour.setImgPath(imgPath);
    cour.setVideoLink(videoLink);
    cour.setContentCour(contentCour);
    cour.setIsVideoLocal(isVideoLocal);
    Cour saveAb = courRepository.save(cour);
    return saveAb;
  }

  public void delete(Cour cour) {
    courRepository.delete(cour);
  }

  public List<Cour> findAll() {
    return courRepository.findAll();
  }

  public List<Cour> findByStatus() {
    return courRepository.findByStatus(Boolean.TRUE);
  }
}
