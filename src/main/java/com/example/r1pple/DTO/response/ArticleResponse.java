package com.example.r1pple.DTO.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ArticleResponse {
    private Long articleId;
    private String title;
    private String content;
    private Long authorId;
    private LocalDateTime createTime;
}

