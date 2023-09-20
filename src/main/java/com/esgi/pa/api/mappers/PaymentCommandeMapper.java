package com.esgi.pa.api.mappers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.esgi.pa.api.dtos.responses.stripe.GetPaymentCommandeResponse;
import com.esgi.pa.domain.entities.PaymentCommande;

public interface PaymentCommandeMapper {
  static GetPaymentCommandeResponse toGetPaymentResponse(PaymentCommande payment) {
    return new GetPaymentCommandeResponse(
        payment.getId(),
        payment.getAmount(),
        ItemPaymentMapper.toGetItemPaymentResponse(payment.getItemPayment()),
        UserMapper.toGetUserResponse(payment.getUsers()),
        DateConverter(payment.getPaymentDate()),
        payment.getReceiptPath(),
        payment.getNumeroRue(),
        payment.getNumeroRueCompl(),
        payment.getCodePostal(),
        payment.getTelephone(),
        payment.getVille(),
        payment.getNoteCommande(),
        InternMapper.toGetInternResponse(payment.getLivreur()),
        payment.getStatusCommande());
  }

  static List<GetPaymentCommandeResponse> toGetPaymentResponse(List<PaymentCommande> entities) {
    return entities
        .stream()
        .map(PaymentCommandeMapper::toGetPaymentResponse)
        .distinct()
        .toList();
  }

  private static String DateConverter(Date paymentDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    return sdf.format(paymentDate);
  }

}
