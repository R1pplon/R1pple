package com.example.r1pple.service;

import com.example.r1pple.DTO.response.ArticleResponse;

public interface ArticleService {
    ArticleResponse getArticleById(Long articleId);
}