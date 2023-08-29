package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.CategorieMateriel;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.CategorieMaterielRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des utilisateurs
 */
@Service
@RequiredArgsConstructor
public class CategorieMaterielService {

  private final CategorieMaterielRepository categorieMaterielRepository;

  public CategorieMateriel getById(Long id) throws TechnicalNotFoundException {
    return categorieMaterielRepository
      .findById(id)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No categorieMateriel found with following id : "
        )
      );
  }

  public CategorieMateriel create(Intern creator, String name)
    throws TechnicalFoundException {
    CategorieMateriel saveAb = categorieMaterielRepository.save(
      CategorieMateriel.builder().name(name).creator(creator).build()
    );
    return saveAb;
  }

  public CategorieMateriel update(
    CategorieMateriel categorieMateriel,
    String name
  ) {
    categorieMateriel.setName(name);
    CategorieMateriel saveAb = categorieMaterielRepository.save(
      categorieMateriel
    );
    return saveAb;
  }

  public void delete(CategorieMateriel categorieMateriel) {
    categorieMaterielRepository.delete(categorieMateriel);
  }

  public List<CategorieMateriel> findAll() {
    return categorieMaterielRepository.findAll();
  }
}
