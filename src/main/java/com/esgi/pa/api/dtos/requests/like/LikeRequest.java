package com.esgi.pa.api.dtos.requests.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeRequest {
    private Long postId;
    private Long userId;
}