package com.example.r1pple.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // 显式映射列名
    private Long userId;      // 使用驼峰命名

    @Column(nullable = false, unique = true)
    private String nickname;

    // 用户与文章的 1:N 关系（懒加载 + 级联删除）
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore  // 防止JSON无限递归
    private List<Article> articles = new ArrayList<>();

    // 用户与评论的 1:N 关系
    @OneToMany(mappedBy = "commenter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();
}