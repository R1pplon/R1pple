package com.example.r1pple.controller;

import com.example.r1pple.DTO.response.ArticleResponse;
import com.example.r1pple.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@Tag(name = "文章接口")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/{articleId}")
    @Operation(summary = "根据ID获取文章信息")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable Integer articleId) {
        return ResponseEntity.ok(articleService.getArticleById(articleId));
    }

    // 获取所有文章
    @GetMapping("/all")
    @Operation(summary = "获取所有文章",
            description = "返回按时间倒序排列的文章列表")
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }
}