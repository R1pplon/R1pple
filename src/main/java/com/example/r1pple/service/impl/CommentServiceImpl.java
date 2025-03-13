package com.example.r1pple.service.impl;

import com.example.r1pple.DTO.response.CommentResponse;
import com.example.r1pple.model.Comment;
import com.example.r1pple.repository.CommentRepository;
import com.example.r1pple.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public CommentResponse getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .map(this::convertToCommentResponse)
                .orElseThrow(() -> new EntityNotFoundException("评论不存在"));
    }

    private CommentResponse convertToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .articleId(comment.getArticle().getArticleId())
                .commenterName(comment.getCommenter().getNickname())
                .createTime(comment.getCreateTime())
                .build();
    }
}
