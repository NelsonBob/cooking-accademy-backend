package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.post.PostGetResponse;
import com.esgi.pa.domain.entities.Post;

import java.util.List;

public interface PostMapper {

    static PostGetResponse toPostGetResponse(Post post){
        return new PostGetResponse(
                post.getId(),
                post.getUser().getId(),
                post.getUser().getName(),
                post.getDescription()
        );
    }
    static List<PostGetResponse> toPostGetResponse(List<Post> entities){
        return entities
                .stream()
                .map(PostMapper::toPostGetResponse)
                .distinct()
                .toList();
    }

}