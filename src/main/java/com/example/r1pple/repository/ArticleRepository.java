package com.example.r1pple.repository;

import com.example.r1pple.model.Article;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    // 查询所有文章，并加载关联的实体
    // 使用 EntityGraph 来加载关联的实体，避免 N+1 查询
    @EntityGraph(attributePaths = {"author", "comments"})
    @Query("SELECT a FROM Article a ORDER BY a.createTime DESC")
    List<Article> findAllArticlesWithRelations();
}