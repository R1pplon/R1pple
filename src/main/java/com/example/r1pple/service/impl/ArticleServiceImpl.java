package com.example.r1pple.service.impl;

import com.example.r1pple.DTO.response.ArticleListResponse;
import com.example.r1pple.DTO.response.ArticleResponse;
import com.example.r1pple.model.Article;
import com.example.r1pple.repository.ArticleRepository;
import com.example.r1pple.service.ArticleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    // 根据ID获取文章信息
    @Override
    public ArticleResponse getArticleById(Integer articleId) {
        return articleRepository.findById(articleId)
                .map(this::convertToArticleResponse)
                .orElseThrow(() -> new EntityNotFoundException("文章不存在"));
    }

    // 将Article对象转换为ArticleResponse对象
    private ArticleResponse convertToArticleResponse(Article article) {
        return ArticleResponse.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .content(article.getContent())
                .authorId(article.getAuthor().getUserId())
                .createTime(article.getCreateTime())
                .build();
    }

    // 获取所有文章
    @Override
    @Transactional(readOnly = true)
    public List<ArticleListResponse> getAllArticles() {
        return articleRepository.findAllArticlesWithRelations().stream()
                .map(this::convertToArticleListResponse)
                .collect(Collectors.toList());
    }

    // 将Article对象转换为ArticleListResponse对象
    private ArticleListResponse convertToArticleListResponse(Article article) {
        return ArticleListResponse.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .authorName(article.getAuthor().getNickname()) // 获取作者昵称
                .createTime(article.getCreateTime())
                .commentCount(article.getComments().size()) // 统计评论数
                .build();
    }
}
