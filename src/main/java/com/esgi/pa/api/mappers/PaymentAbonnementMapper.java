package com.esgi.pa.api.mappers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.esgi.pa.api.dtos.responses.stripe.GetPaymentAbonnementResponse;
import com.esgi.pa.domain.entities.PaymentAbonnement;

public interface PaymentAbonnementMapper {
  static GetPaymentAbonnementResponse toGetPaymentAbonnementResponse(
    PaymentAbonnement payment
  ) {
    
    return new GetPaymentAbonnementResponse(
      payment.getId(),
      payment.getAmount(),
      UserMapper.toGetUserResponse(payment.getUsers()),
      payment.getTypeAbonnement(),
      DateConverter(payment.getPaymentDate()),
      payment.getReceiptPath()
    );
  }

  static List<GetPaymentAbonnementResponse> toGetPaymentAbonnementResponse(
    List<PaymentAbonnement> entities
  ) {
    return entities
      .stream()
      .map(PaymentAbonnementMapper::toGetPaymentAbonnementResponse)
      .distinct()
      .toList();
  }

  private static String DateConverter(Date paymentDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    return sdf.format(paymentDate);
  }
}
