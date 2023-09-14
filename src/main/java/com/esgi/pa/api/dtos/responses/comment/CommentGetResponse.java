package com.esgi.pa.api.dtos.responses.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentGetResponse {
    private Long id;
    private Long postId;
    private Long userId;
    private String userName;
    private String description;
}