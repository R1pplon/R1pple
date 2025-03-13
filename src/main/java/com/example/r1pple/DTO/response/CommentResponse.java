package com.example.r1pple.DTO.response;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private Integer commentId; // 评论ID
    private String content; // 评论内容
    private Integer articleId;  // 关联的文章ID
    private String commenterName;    // 评论者名称
    private LocalDateTime createTime;   // 创建时间
}
