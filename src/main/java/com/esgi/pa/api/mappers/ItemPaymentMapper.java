package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.stripe.ItemPaymentCommandeResponse;
import com.esgi.pa.domain.entities.ItemPayment;

public interface ItemPaymentMapper {
  static ItemPaymentCommandeResponse toGetItemPaymentResponse(ItemPayment itemPayment) {
    return new ItemPaymentCommandeResponse(
        itemPayment.getId(),
        itemPayment.getPrice(),
        itemPayment.getName(),
        itemPayment.getItemTotal(),
        itemPayment.getQuantity(),
        itemPayment.getType());
  }

  static List<ItemPaymentCommandeResponse> toGetItemPaymentResponse(List<ItemPayment> entities) {
    return entities
        .stream()
        .map(ItemPaymentMapper::toGetItemPaymentResponse)
        .distinct()
        .toList();
  }

}
