package com.esgi.pa.api.resources;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.responses.cour.GetCourResponse;
import com.esgi.pa.api.dtos.responses.intern.GetInternResponse;
import com.esgi.pa.api.dtos.responses.materiel.GetMaterielResponse;
import com.esgi.pa.api.dtos.responses.optionAbonnement.GetOptionAbonnementResponse;
import com.esgi.pa.api.dtos.responses.repas.GetRepasResponse;
import com.esgi.pa.api.dtos.responses.salle.GetSalleResponse;
import com.esgi.pa.api.dtos.responses.serviceAbonnement.GetServiceAbonnementResponse;
import com.esgi.pa.api.mappers.CourMapper;
import com.esgi.pa.api.mappers.InternMapper;
import com.esgi.pa.api.mappers.MaterielMapper;
import com.esgi.pa.api.mappers.OptionAbonnementMapper;
import com.esgi.pa.api.mappers.RepasMapper;
import com.esgi.pa.api.mappers.SalleMapper;
import com.esgi.pa.api.mappers.ServiceAbonnementMapper;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.CourService;
import com.esgi.pa.domain.services.InternService;
import com.esgi.pa.domain.services.MaterielService;
import com.esgi.pa.domain.services.OptionAbonnementService;
import com.esgi.pa.domain.services.RepasService;
import com.esgi.pa.domain.services.SalleService;
import com.esgi.pa.domain.services.ServiceAbonnementService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

/**
 * Contient les routes publics
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
@Api(tags = "Api Publiques API")
public class ApiPublicRessource {
    private final CourService courService;
    private final ServiceAbonnementService serviceAbonnementService;
    private final OptionAbonnementService optionAbonnementService;
    private final MaterielService materielService;
    private final RepasService repasService;
    private final InternService internService;
    private final SalleService salleService;

    @GetMapping("cours")
    public List<GetCourResponse> getCoursActif() {
        return CourMapper.toGetCourResponse(courService.findByStatus());
    }

    @GetMapping("services")
    public List<GetServiceAbonnementResponse> getServiceAbonnementsActif() {
        return ServiceAbonnementMapper.toGetServiceAbonnementResponse(
                serviceAbonnementService.findByStatus());
    }

    @GetMapping("options-service")
    public List<GetOptionAbonnementResponse> getOptionAbonnementsActif() {
        return OptionAbonnementMapper.toGetOptionAbonnementResponse(
                optionAbonnementService.findByStatus());
    }

    @GetMapping("materiels")
    public List<GetMaterielResponse> getMaterielsActif() {
        return MaterielMapper.toGetMaterielResponse(
                materielService.findByStatus());
    }

    @GetMapping("repas")
    public List<GetRepasResponse> getRepasActif() {
        return RepasMapper.toGetRepasResponse(
                repasService.findByStatus());
    }

    @GetMapping("chefs")
    public List<GetInternResponse> getInternActif() {
        return InternMapper.toGetInternResponse(
                internService.findTop4Chefs());
    }

    @GetMapping("salles")
    public List<GetSalleResponse> getSalleActif() {
        return SalleMapper.toGetSalleResponse(
                salleService.findByStatus());
    }

    @GetMapping("salles/{idk}")
    public GetSalleResponse getSalleById(
            @PathVariable Long idk) throws TechnicalNotFoundException {
        return SalleMapper.toGetSalleResponse(salleService.getById(idk));
    }
}
