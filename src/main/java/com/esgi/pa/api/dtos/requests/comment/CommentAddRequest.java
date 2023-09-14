package com.esgi.pa.api.dtos.requests.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentAddRequest {
    private Long postId;
    private String description;
}