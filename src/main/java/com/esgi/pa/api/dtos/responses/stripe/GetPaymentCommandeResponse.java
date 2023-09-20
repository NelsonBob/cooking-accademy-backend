package com.esgi.pa.api.dtos.responses.stripe;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.List;

import com.esgi.pa.api.dtos.responses.intern.GetInternResponse;
import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.esgi.pa.domain.enums.StatusCommandeEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetPaymentCommandeResponse(
        Long id,
        Integer amount,
        List<ItemPaymentCommandeResponse> items,
        GetUserResponse user,
        String paymentDate,
        String receiptPath,
        String numeroRue,
        String numeroRueCompl,
        String codePostal,
        Integer telephone,
        String ville,
        String noteCommande,
        GetInternResponse livreur,
        StatusCommandeEnum statusCommande) {
}
