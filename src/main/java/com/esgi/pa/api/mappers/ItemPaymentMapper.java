package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.stripe.ItemPaymentResponse;
import com.esgi.pa.domain.entities.ItemPayment;

public interface ItemPaymentMapper {
  static ItemPaymentResponse toGetItemPaymentResponse(ItemPayment itemPayment) {
    return new ItemPaymentResponse(
        itemPayment.getId(),
        itemPayment.getPrice(),
        itemPayment.getName(),
        itemPayment.getItemTotal(),
        itemPayment.getQuantity(),
        itemPayment.getType());
  }

  static List<ItemPaymentResponse> toGetItemPaymentResponse(List<ItemPayment> entities) {
    return entities
        .stream()
        .map(ItemPaymentMapper::toGetItemPaymentResponse)
        .distinct()
        .toList();
  }

}
