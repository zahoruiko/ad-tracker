package com.example.spring.adstracker.repository;

import com.example.spring.adstracker.data.Article;
import com.example.spring.adstracker.data.User;
import java.sql.Timestamp;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Oleksii Zahoruiko
 */
public interface ArticleRepo extends JpaRepository<Article, Long> {
    
    Article getByArticleId(Long articleId);

    Article findFirstByThemeOrderByCreatedDesc(String theme);  
    
    
    @Query("SELECT a.articleId, a.title, a.body, a.created "
           + "FROM Article a "
          + "WHERE a.theme = ?1 "
            + "AND a.visible = 'true'"
       + "ORDER BY a.created DESC")    
    Page<Article> getVisibleArticlesByThema(String theme, Pageable pageable);
    
    
    @Query("SELECT a.articleId, a.title, a.body, a.created "
           + "FROM Article a "
          + "WHERE a.theme = ?1 "
       + "ORDER BY a.created DESC")    
    Page<Article> getAllArticlesByThema(String theme, Pageable pageable);
    
    
    @Query("SELECT a.articleId, a.title, a.body, a.created "
           + "FROM Article a "
       + "ORDER BY a.created DESC")    
    Page<Article> getAllArticles(Pageable pageable);
    
    
    @Query("SELECT a.articleId, a.title, a.body, a.created "
           + "FROM Article a "
          + "WHERE a.visible = 'true' "
            + "AND mainPage = 'true' "
       + "ORDER BY a.created DESC")    
    Page<Article> getArticlesForMainPage(int limit, Pageable pageable);

    
    @Transactional
    @Modifying
    @Query("UPDATE Article a "
            + "SET a.title = ?3,"
                + "a.body = ?4,"
                + "a.theme = ?5,"
                + "a.created = ?6 "
          + "WHERE a.user = ?1 "
            + "AND a.articleId = ?2")
    int updateArticle(User user, long articleId, String title, String body, String theme, Timestamp created);
    

    @Transactional
    @Modifying
    @Query("DELETE "
           + "FROM Article a "
          + "WHERE a.user = ?1 "
            + "AND a.articleId = ?2")
    void deleteCampaignByUserAndCode(User user, long articleId);  
    
}