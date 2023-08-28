package com.esgi.pa.api.dtos.responses.cour;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.api.dtos.responses.intern.GetInternResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetCourResponse(
    Long id,
    String name,
    String description,
    String imgPath,
    String videoLink,
    String contentCour,
    Boolean status,
    Boolean isVideoLocal,
    GetInternResponse creator) {
        
}
