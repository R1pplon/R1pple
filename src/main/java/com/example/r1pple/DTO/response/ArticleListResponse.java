package com.example.r1pple.DTO.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ArticleListResponse {
    private Integer articleId; // 文章ID
    private String title;   // 文章标题
    private String authorName;  // 作者名称
    private LocalDateTime createTime;   // 文章发布时间
    private Integer commentCount;   // 文章下评论数量
}
