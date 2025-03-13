# API

## 获取所有文章

1. **接口地址**：`/api/articles/all`
2. **请求方式**：GET

### Model

model 确定**数据属性**

```java
package com.example.r1pple.model;

@Getter
@Setter
@Entity
@Table(name = "article")
public class Article {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "article_id") // 显式映射列名
  private Long articleId;      // 使用驼峰命名

  @CreatedDate
  @Column(name = "create_time", updatable = false)
  private LocalDateTime createTime;

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
```

### Response

response 确定返回**数据结构**

```java
@Data
@Builder
public class ArticleListResponse {
    private Long articleId; // 文章ID
    private String title;   // 文章标题
    private String authorName;  // 作者名称
    private LocalDateTime createTime;   // 文章发布时间
    private int commentCount;   // 文章下评论数量
}
```

### Repository

Repository 提供**数据访问逻辑**

```java
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    // 查询所有文章，并加载关联的实体
    // 使用 EntityGraph 来加载关联的实体，避免 N+1 查询
    @EntityGraph(attributePaths = {"author", "comments"})
    @Query("SELECT a FROM Article a ORDER BY a.createTime DESC")
    List<Article> findAllArticlesWithRelations();
}
```

### Service

Service 提供**业务逻辑**

```java
// 接口
public interface ArticleService {
    List<ArticleListResponse> getAllArticles();
}

// 实现
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    // 获取所有文章
    @Override
    @Transactional(readOnly = true)
    public List<ArticleListResponse> getAllArticles() {
        return articleRepository.findAllArticlesWithRelations().stream()
                .map(this::convertToArticleListResponse)
                .collect(Collectors.toList());
    }

    // 将Article对象转换为ArticleListResponse对象
    private ArticleListResponse convertToArticleListResponse(Article article) {
        return ArticleListResponse.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .authorName(article.getAuthor().getNickname()) // 获取作者昵称
                .createTime(article.getCreateTime())
                .commentCount(article.getComments().size()) // 统计评论数
                .build();
    }
}
```

### Controller

Controller 提供**API 接口**

```java
@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@Tag(name = "文章接口")
public class ArticleController {
    private final ArticleService articleService;

    // 获取所有文章
    @GetMapping("/all")
    @Operation(summary = "获取所有文章",
            description = "返回按时间倒序排列的文章列表")
    public ResponseEntity<List<ArticleListResponse>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }
}
```

## 获取所有用户

1. **接口地址**：`/api/users/all`
2. **请求方式**：GET

### Model

```java
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // 显式映射列名
    private Long userId;      // 使用驼峰命名

    @CreatedDate
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

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
```

### Response

```java
@Data
@Builder
public class UserListResponse {
    private Long userId;    // 用户ID
    private String nickname;    // 昵称
    private int articlesCount; // 发布文章数量
    private LocalDateTime createTime;   // 注册时间
    private int commentCount;   // 发布评论数量
}
```

### Repository

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 查询所有用户
    @Query("SELECT u FROM User u ORDER BY u.createTime DESC")
    List<User> findAllUsersWithRelations();
}
```

### Service

```java
// 接口
public interface UserService {
    List<UserListResponse> getAllUsers();
}

// 实现
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    // 获取所有用户信息
    @Override
    @Transactional(readOnly = true)
    public List<UserListResponse> getAllUsers() {
        return userRepository.findAllUsersWithRelations().stream()
                .map(this::convertToUserListResponse)
                .collect(Collectors.toList());
    }

    // 将User对象转换为UserResponse对象
    private UserListResponse convertToUserListResponse(User user) {
        return UserListResponse.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .createTime(user.getCreateTime())
                .articlesCount(user.getArticles().size())
                .commentCount(user.getComments().size())
                .build();
    }
}
```

### Controller

```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "用户接口")
public class UserController {
    private final UserService userService;

    // 获取所有用户
    @GetMapping("/all")
    @Operation(summary = "获取所有用户")
    public ResponseEntity<List<UserListResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
```
