package com.example.r1pple.DTO.response;

import com.example.r1pple.model.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ArticleResponse {
    private Integer articleId; // 文章ID
    private String title;   // 文章标题
    private String content; // 文章内容
    private String authorName;  // 作者名称
    private LocalDateTime createTime;   // 文章发布时间
    private Integer commentCount;   // 文章下评论数量
    private List<CommentResponse> comments; // 评论信息列表
}

