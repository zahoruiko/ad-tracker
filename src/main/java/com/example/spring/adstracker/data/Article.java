package com.example.spring.adstracker.data;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Oleksii Zahoruiko
 */
@Entity
@Table(name="articles")
public class Article {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable=false, unique=true)
    private Long articleId; // Integer
    
    @Length(min = 1, max = 255, message = "The title must be not empty and no longer than 255 chars")
    @Column(nullable=false, unique=false)
    private String title;
    
    @Length(min = 1, max = 10240, message = "The text must be not empty and no longer than 255 chars")
    @Column(nullable=false, unique=false)
    private String body;
    
    @Length(min = 5, max = 8, message = "Please select the theme")
    @Column(nullable=false, unique=false)
    private String theme;
    
    @Column(nullable=false, unique=false)
    private Timestamp created;
    
    @Column(nullable=false, unique=false)
    private boolean mainPage = false;
    
    @Column(nullable=false, unique=false)
    private boolean visible = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private User user;

    public Article() {
    }

    public Article(String title, 
                   String body, 
                   String theme, 
                   Timestamp created, 
                   User user) {
        
        this.title = title;
        this.body = body;
        this.theme = theme;
        this.created = created;
        this.user = user;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    
    public Long getArticleId() {
        return articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isMainPage() {
        return mainPage;
    }

    public void setMainPage(boolean mainPage) {
        this.mainPage = mainPage;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }   
}