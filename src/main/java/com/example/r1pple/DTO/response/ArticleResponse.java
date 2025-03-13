package com.example.r1pple.DTO.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ArticleResponse {
    private Integer articleId;
    private String title;
    private String content;
    private Integer authorId;
    private LocalDateTime createTime;
}

