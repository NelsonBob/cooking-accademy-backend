package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.like.LikeResponse;
import com.esgi.pa.domain.entities.Like;

import java.util.List;

public interface LikeMapper {
    static LikeResponse toLikeResponse(Like like){
        return new LikeResponse(
                like.getId(),
                like.getUser().getId(),
                like.getUser().getName()
        );
    }

    static List<LikeResponse> toLikeResponse(List<Like> likes){
        return likes
                .stream()
                .map(LikeMapper::toLikeResponse)
                .distinct()
                .toList();
    }
}
