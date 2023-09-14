package com.esgi.pa.api.dtos.responses.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostGetResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String Description;
}