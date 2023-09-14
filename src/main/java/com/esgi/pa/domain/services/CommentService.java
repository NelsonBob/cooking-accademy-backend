package com.esgi.pa.domain.services;

import com.esgi.pa.api.dtos.requests.comment.CommentAddRequest;
import com.esgi.pa.api.dtos.requests.comment.CommentUpdateRequest;
import com.esgi.pa.domain.entities.Comment;
import com.esgi.pa.server.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;


    public void add(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> getAll() {

        return commentRepository.findAll();
    }

    public Comment getById(Long id) {

        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> getAllByPost(Long postId) {
        return commentRepository.findAllByPost_Id(postId);
    }

    public List<Comment> getAllByUser(Long userId) {
        return commentRepository.findAllByUser_Id(userId);
    }

    public void update(Long id, CommentUpdateRequest commentUpdateRequest) {
        Comment commentToUpdate = commentRepository.findById(id).orElse(null);
        if (commentToUpdate != null) {
            commentToUpdate.setDescription(commentUpdateRequest.getDescription());
        }
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}