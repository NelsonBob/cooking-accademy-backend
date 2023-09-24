package com.esgi.pa.domain.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esgi.pa.api.dtos.responses.repas.GetRepasResponse;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Repas;
import com.esgi.pa.domain.enums.TypeCommandeEnum;
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
  private final PaymentCommandeService paymentCommandeService;

  public GetRepasResponse getById(Long id) throws TechnicalNotFoundException {
    Repas repas = repasRepository
      .findById(id)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No repas found with following id : "
        )
      );
    return new GetRepasResponse(
      repas.getId(),
      repas.getName(),
      repas.getDescription(),
      repas.getImgPath(),
      repas.getQuantity(),
      repas.getPrice(),
      repas.getStatus(),
      paymentCommandeService.valueAvis(TypeCommandeEnum.Repas, repas.getId())
    );
  }

  public Repas getByIdRepas(Long id) throws TechnicalNotFoundException {
    return repasRepository
      .findById(id)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No repas found with following id : "
        )
      );
  }

  public Repas create(
    Intern creator,
    String name,
    String description,
    String imgPath,
    Integer quantity,
    Float price
  ) throws TechnicalFoundException {
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
          .build()
      );
      return saveAb;
    } else {
      throw new TechnicalFoundException(
        "Un repas existe Déjà avec ce nom :" + name
      );
    }
  }

  public Repas update(
    Repas repas,
    String name,
    String description,
    String imgPath,
    Integer quantity,
    Float price
  ) throws TechnicalFoundException {
    if (
      repasRepository.findByName(name).isEmpty() ||
      repasRepository.findByName(name).isPresent() &&
      repasRepository.findByName(name).get().getId() == repas.getId()
    ) {
      repas.setName(name);
      repas.setDescription(description);
      repas.setPrice(price);
      repas.setQuantity(quantity);
      repas.setStatus(quantity <= 0 ? Boolean.FALSE : Boolean.TRUE);
      repas.setImgPath(imgPath);
      Repas saveAb = repasRepository.save(repas);
      return saveAb;
    } else {
      throw new TechnicalFoundException(
        "Un repas existe Déjà avec ce nom :" + name
      );
    }
  }

  public void delete(Repas repas) {
    repasRepository.delete(repas);
  }

  public List<GetRepasResponse> findAll() {
    List<Repas> list = repasRepository.findAll();

    List<GetRepasResponse> getRepasResponses = new ArrayList<>();
    list.forEach(repas -> {
      getRepasResponses.add(
        new GetRepasResponse(
          repas.getId(),
          repas.getName(),
          repas.getDescription(),
          repas.getImgPath(),
          repas.getQuantity(),
          repas.getPrice(),
          repas.getStatus(),
          paymentCommandeService.valueAvis(
            TypeCommandeEnum.Repas,
            repas.getId()
          )
        )
      );
    });
    return getRepasResponses;
  }

  public List<GetRepasResponse> findByStatus() {
    List<Repas> list = repasRepository.findByStatusOrderById(Boolean.TRUE);
    List<GetRepasResponse> getRepasResponses = new ArrayList<>();
    list.forEach(repas -> {
      getRepasResponses.add(
        new GetRepasResponse(
          repas.getId(),
          repas.getName(),
          repas.getDescription(),
          repas.getImgPath(),
          repas.getQuantity(),
          repas.getPrice(),
          repas.getStatus(),
          paymentCommandeService.valueAvis(
            TypeCommandeEnum.Repas,
            repas.getId()
          )
        )
      );
    });
    return getRepasResponses;
  }
}
