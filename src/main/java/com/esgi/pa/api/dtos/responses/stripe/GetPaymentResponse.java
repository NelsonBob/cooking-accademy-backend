package com.esgi.pa.api.dtos.responses.stripe;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.List;

import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetPaymentResponse(
        Integer amount,
        List<ItemPaymentResponse> items,
        GetUserResponse user) {
}
