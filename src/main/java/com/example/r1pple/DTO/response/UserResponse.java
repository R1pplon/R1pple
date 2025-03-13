package com.example.r1pple.DTO.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Integer userId;    // 用户ID
    private String nickname;    // 昵称
    private Integer articlesCount; // 发布文章数量
    private LocalDateTime createTime;   // 注册时间
    private Integer commentCount;   // 发布评论数量
}
