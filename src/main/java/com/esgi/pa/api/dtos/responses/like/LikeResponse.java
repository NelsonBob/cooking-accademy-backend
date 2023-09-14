package com.esgi.pa.api.dtos.responses.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponse {
    private Long id;
    private Long userId;
    private String name;
}