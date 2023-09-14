package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Post;
import com.esgi.pa.server.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post  getById(Long postId){
        return postRepository.findPostById(postId);
    }


}
