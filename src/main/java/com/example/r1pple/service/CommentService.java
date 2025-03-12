package com.example.r1pple.service;

import com.example.r1pple.DTO.response.CommentResponse;

public interface CommentService {
    CommentResponse getCommentById(Long commentId);
}
