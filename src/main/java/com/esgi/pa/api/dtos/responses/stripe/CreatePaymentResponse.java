package com.esgi.pa.api.dtos.responses.stripe;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreatePaymentResponse {

  private String clientSecret;
}
