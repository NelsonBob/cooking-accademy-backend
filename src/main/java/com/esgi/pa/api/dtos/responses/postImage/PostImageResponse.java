package com.esgi.pa.api.dtos.responses.postImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostImageResponse {
    private Long id;
    private String name;
    private String type;
    private byte[] data;
    private Long postId;
}