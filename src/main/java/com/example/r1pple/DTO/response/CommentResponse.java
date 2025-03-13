package com.example.r1pple.DTO.response;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private Integer commentId;
    private String content;
    private Integer articleId;
    private Integer commenterId;
    private LocalDateTime createTime;
}
