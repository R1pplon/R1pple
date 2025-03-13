package com.example.r1pple.service;

import com.example.r1pple.DTO.response.ArticleResponse;

import java.util.List;

public interface ArticleService {
    ArticleResponse getArticleById(Integer articleId);
    List<ArticleResponse> getAllArticles();
}