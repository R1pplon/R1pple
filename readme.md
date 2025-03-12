# R1pple个人博客构建过程

## 后端

spring boot + JPA + MySQL

### 依赖项

```
spring boot devtools
lombok
spring web
spring data JPA
```

### 配置

项目结构

```txt
src/main/java
└── com
    └── yourblog
        ├── BlogApplication.java          # 启动类
        │
        ├── config/                       # 配置类目录
        │   ├── SecurityConfig.java       # 安全配置
        │   ├── SwaggerConfig.java        # API文档配置
        │   └── WebMvcConfig.java         # 全局配置
        │
        ├── controller/                   # 控制器层
        │   ├── UserController.java
        │   ├── ArticleController.java
        │   └── CommentController.java
        │
        ├── service/                      # 服务层
        │   ├── UserService.java          # 服务接口
        │   ├── impl/                     
        │   │   └── UserServiceImpl.java  # 服务实现
        │   ├── ArticleService.java
        │   └── CommentService.java
        │
        ├── repository/                   # 数据访问层
        │   ├── UserRepository.java       # JPA Repository
        │   ├── ArticleRepository.java
        │   └── CommentRepository.java
        │
        ├── model/                        # 实体模型
        │   ├── User.java
        │   ├── Article.java 
        │   └── Comment.java
        │
        ├── dto/                          # 数据传输对象
        │   ├── request/                  # 入参DTO
        │   │   ├── CreateUserRequest.java
        │   │   └── UpdateArticleRequest.java
        │   └── response/                 # 出参DTO
        │       ├── UserResponse.java
        │       └── ArticleDetailResponse.java
        │
        └── exception/                    # 异常处理
            ├── GlobalExceptionHandler.java # 全局异常处理器
            ├── BusinessException.java    # 自定义业务异常
            └── ErrorCode.java            # 错误码枚举
```

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/blog_db
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### model

总结步骤：

1. 创建User实体，包含id、nickname，以及@OneToMany关联到Article和Comment。
2. 创建Article实体，包含id，与User的@ManyToOne关联，以及@OneToMany关联到Comment。
3. 创建Comment实体，包含id，与User和Article的@ManyToOne关联。
4. 设置级联操作和懒加裁，避免性能问题。
5. 处理JSON序列化的循环引用问题。
6. 示例代码和配置说明，确保用户能正确实现关系映射。

#### User

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

#### Article

```java
@Getter
@Setter
@Entity
@Table(name = "article")
public class Article {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "article_id") // 显式映射列名
  private Long articleId;      // 使用驼峰命名

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

#### Comment

```java
@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id") // 显式映射列名
    private Long commentId;      // 使用驼峰命名

    @Column(nullable = false, length = 500)
    private String content;

    // 多个评论属于一个用户 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User commenter;

    // 多个评论属于一篇文章 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;
}
```

### Repository

#### ArticleRepository

```java
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
```

#### CommentRepository

```java
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
```

#### UserRepository

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
```
