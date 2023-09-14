package com.esgi.pa.api.dtos.requests.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostAddRequest {
    private Long userId;
    private String Description;
}