package com.example.r1pple.DTO.response;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private Long commentId;
    private String content;
    private Long articleId;
    private Long commenterId;
    private LocalDateTime createTime;
}
