package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.postImage.PostImageResponse;
import com.esgi.pa.domain.entities.PostImage;

import java.util.List;

public interface PostImageMapper {
    static PostImageResponse toPostImageResponse(PostImage postImage){
        return new PostImageResponse(
                postImage.getId(),
                postImage.getName(),
                postImage.getType(),
                postImage.getData(),
                postImage.getPost().getId()
        );
    }

    static List<PostImageResponse> toPostImageResponse(List<PostImage> postImageResponses)
    {
        return postImageResponses
                .stream()
                .map(PostImageMapper::toPostImageResponse)
                .distinct()
                .toList();
    }
}
