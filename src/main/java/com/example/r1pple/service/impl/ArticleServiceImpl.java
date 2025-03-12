package com.example.r1pple.service.impl;

import com.example.r1pple.DTO.response.ArticleResponse;
import com.example.r1pple.model.Article;
import com.example.r1pple.repository.ArticleRepository;
import com.example.r1pple.service.ArticleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    @Override
    public ArticleResponse getArticleById(Long articleId) {
        return articleRepository.findById(articleId)
                .map(this::convertToArticleResponse)
                .orElseThrow(() -> new EntityNotFoundException("文章不存在"));
    }

    private ArticleResponse convertToArticleResponse(Article article) {
        return ArticleResponse.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .content(article.getContent())
                .authorId(article.getAuthor().getUserId())
                .createTime(article.getCreateTime())
                .build();
    }
}
