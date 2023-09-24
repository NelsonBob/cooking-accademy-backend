package com.esgi.pa.domain.services;

import com.esgi.pa.api.dtos.requests.gallerie.GallerieRequest;
import com.esgi.pa.api.dtos.responses.materiel.GetMaterielResponse;
import com.esgi.pa.api.mappers.CategorieMaterielMapper;
import com.esgi.pa.api.mappers.InternMapper;
import com.esgi.pa.domain.entities.CategorieMateriel;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Materiel;
import com.esgi.pa.domain.enums.TypeCommandeEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.MaterielRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterielService {

  private final MaterielRepository materielRepository;
  private final PaymentCommandeService paymentCommandeService;

  public GetMaterielResponse getById(Long id)
    throws TechnicalNotFoundException {
    Materiel materiel = materielRepository
      .findById(id)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No materiel found with following id : "
        )
      );
    return new GetMaterielResponse(
      materiel.getId(),
      materiel.getName(),
      materiel.getDescription(),
      materiel.getImgPath(),
      materiel.getStatus(),
      convertToEntityAttribute(materiel.getGallerie()),
      materiel.getQuantity(),
      materiel.getPrice(),
      CategorieMaterielMapper.toGetCategorieMaterielItemResponse(
        materiel.getCategorieMateriel()
      ),
      InternMapper.toGetInternResponse(materiel.getCreator()),
      paymentCommandeService.valueAvis(
        TypeCommandeEnum.Materiel,
        materiel.getId()
      )
    );
  }

  public Materiel getByIdMateriel(Long id) throws TechnicalNotFoundException {
    return materielRepository
      .findById(id)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No materiel found with following id : "
        )
      );
  }

  public void create(
    Intern creator,
    String name,
    String description,
    String imgPath,
    Integer quantity,
    Float price,
    CategorieMateriel categorieMateriel,
    List<GallerieRequest> gallerie
  ) throws TechnicalFoundException {
    if (materielRepository.findByName(name).isEmpty()) {
      materielRepository.save(
        Materiel
          .builder()
          .name(name)
          .description(description)
          .creator(creator)
          .status(Boolean.TRUE)
          .imgPath(imgPath)
          .quantity(quantity)
          .price(price)
          .categorieMateriel(categorieMateriel)
          .gallerie(galleriesList(gallerie).toString())
          .status(quantity <= 0 ? Boolean.FALSE : Boolean.TRUE)
          .build()
      );
    } else {
      throw new TechnicalFoundException(
        "Une materiel existe Déjà avec ce nom :" + name
      );
    }
  }

  public Materiel update(
    Materiel materiel,
    String name,
    String description,
    String imgPath,
    Integer quantity,
    Float price,
    CategorieMateriel categorieMateriel,
    List<GallerieRequest> gallerie
  ) throws TechnicalFoundException {
    if (
      materielRepository.findByName(name).isEmpty() ||
      materielRepository.findByName(name).isPresent() &&
      materielRepository.findByName(name).get().getId() == materiel.getId()
    ) {
      materiel.setName(name);
      materiel.setDescription(description);
      materiel.setImgPath(imgPath);
      materiel.setCategorieMateriel(categorieMateriel);
      materiel.setPrice(price);
      materiel.setQuantity(quantity);
      materiel.setStatus(quantity <= 0 ? Boolean.FALSE : Boolean.TRUE);
      materiel.setGallerie(galleriesList(gallerie).toString());
      Materiel saveAb = materielRepository.save(materiel);
      return saveAb;
    } else {
      throw new TechnicalFoundException(
        "Une materiel existe Déjà avec ce nom :" + name
      );
    }
  }

  public void delete(Materiel materiel) {
    materielRepository.delete(materiel);
  }

  public List<GetMaterielResponse> findAll() {
    List<Materiel> list = materielRepository.findAll();
    List<GetMaterielResponse> getMaterielResponses = new ArrayList<>();
    list.forEach(materiel -> {
      getMaterielResponses.add(
        new GetMaterielResponse(
          materiel.getId(),
          materiel.getName(),
          materiel.getDescription(),
          materiel.getImgPath(),
          materiel.getStatus(),
          convertToEntityAttribute(materiel.getGallerie()),
          materiel.getQuantity(),
          materiel.getPrice(),
          CategorieMaterielMapper.toGetCategorieMaterielItemResponse(
            materiel.getCategorieMateriel()
          ),
          InternMapper.toGetInternResponse(materiel.getCreator()),
          paymentCommandeService.valueAvis(
            TypeCommandeEnum.Materiel,
            materiel.getId()
          )
        )
      );
    });
    return getMaterielResponses;
  }

  public List<GetMaterielResponse> findByStatus() {
    List<Materiel> list = materielRepository.findByStatus(Boolean.TRUE);
    List<GetMaterielResponse> getMaterielResponses = new ArrayList<>();
    list.forEach(materiel -> {
      getMaterielResponses.add(
        new GetMaterielResponse(
          materiel.getId(),
          materiel.getName(),
          materiel.getDescription(),
          materiel.getImgPath(),
          materiel.getStatus(),
          convertToEntityAttribute(materiel.getGallerie()),
          materiel.getQuantity(),
          materiel.getPrice(),
          CategorieMaterielMapper.toGetCategorieMaterielItemResponse(
            materiel.getCategorieMateriel()
          ),
          InternMapper.toGetInternResponse(materiel.getCreator()),
          paymentCommandeService.valueAvis(
            TypeCommandeEnum.Materiel,
            materiel.getId()
          )
        )
      );
    });
    return getMaterielResponses;
  }

  public List<String> galleriesList(List<GallerieRequest> gallerie) {
    List<String> gal = new ArrayList<>();
    gallerie.forEach(el -> {
      gal.add(el.fileName().toString());
    });
    return gal;
  }

  public String[] convertToEntityAttribute(String dbData) {
    dbData = dbData.replaceAll("\\s+", ""); // Remove spaces

    // Remove square brackets
    if (dbData.startsWith("[") && dbData.endsWith("]")) {
      dbData = dbData.substring(1, dbData.length() - 1);
    }

    String[] elements = dbData.split(",");
    for (int i = 0; i < elements.length; i++) {
      elements[i] = elements[i].trim(); // Remove leading/trailing spaces
    }
    return elements;
  }
}
