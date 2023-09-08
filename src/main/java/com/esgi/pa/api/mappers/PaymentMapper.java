package com.esgi.pa.api.mappers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.esgi.pa.api.dtos.responses.stripe.GetPaymentResponse;
import com.esgi.pa.domain.entities.Payment;

public interface PaymentMapper {
  static GetPaymentResponse toGetPaymentResponse(Payment payment) {
    return new GetPaymentResponse(
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

  static List<GetPaymentResponse> toGetPaymentResponse(List<Payment> entities) {
    return entities
        .stream()
        .map(PaymentMapper::toGetPaymentResponse)
        .distinct()
        .toList();
  }

  private static String DateConverter(Date paymentDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    return sdf.format(paymentDate);
  }

}
