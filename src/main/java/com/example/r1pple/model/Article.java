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
@Table(name = "article")
public class Article {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long article_id;

  @Column(nullable = false, length = 200)
  private String title;

  @Lob  // 大文本字段
  @Column(nullable = false)
  private String content;

  // 多篇文章属于一个用户 (N:1)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User author;

  // 文章与评论的 1:N 关系
  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Comment> comments = new ArrayList<>();
}