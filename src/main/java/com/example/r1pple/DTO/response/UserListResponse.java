package com.example.r1pple.DTO.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class UserListResponse {
    private Long userId;    // 用户ID
    private String nickname;    // 昵称
    private int articlesCount; // 发布文章数量
    private LocalDateTime createTime;   // 注册时间
    private int commentCount;   // 发布评论数量
}