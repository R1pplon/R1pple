package com.example.r1pple.service.impl;

import com.example.r1pple.DTO.response.ArticleResponse;
import com.example.r1pple.DTO.response.CommentResponse;
import com.example.r1pple.model.Article;
import com.example.r1pple.model.Comment;
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
        List<CommentResponse> commentResponses = article.getComments().stream()
                .map(this::convertToCommentResponse)
                .collect(Collectors.toList());

        return ArticleResponse.builder()
                .articleId(article.getArticleId())  // 文章ID
                .title(article.getTitle())  // 文章标题
                .content(article.getContent())  // 文章内容
                .authorName(article.getAuthor().getNickname())  // 作者名称
                .createTime(article.getCreateTime())    // 文章发布时间
                .commentCount(article.getComments().size()) // 文章下评论数量
                .comments(commentResponses) // 文章下评论列表
                .build();
    }

    // 将Comment对象转换为CommentResponse对象
    private CommentResponse convertToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .articleId(comment.getArticle().getArticleId())
                .commenterName(comment.getCommenter().getNickname())
                .createTime(comment.getCreateTime())
                .build();
    }

    // 获取所有文章,不含正文
    // 用于列表
    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAllArticlesWithRelations().stream()
                .map(this::convertToArticleListResponse)
                .collect(Collectors.toList());
    }

    // 将Article对象转换为ArticleListResponse对象
    // 不含正文
    private ArticleResponse convertToArticleListResponse(Article article) {
        return ArticleResponse.builder()
                .articleId(article.getArticleId())  // 文章ID
                .title(article.getTitle())  // 文章标题
                .authorName(article.getAuthor().getNickname()) // 获取作者昵称
                .createTime(article.getCreateTime())    // 文章发布时间
                .commentCount(article.getComments().size()) // 文章下评论数量
                .build();
    }
}
