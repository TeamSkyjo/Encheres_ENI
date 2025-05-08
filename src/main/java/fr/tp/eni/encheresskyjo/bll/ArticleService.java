package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.*;

import java.util.List;

public interface ArticleService {
    void createArticle(Article article);
    void updateArticle(Article article);

    Article getArticleById(int articleId);
    List<Article> getByStatus(ArticleStatus articleStatus);
    List<Article> getArticles();
    List<Article> getFilteredArticles(String pattern, Category category);
    void deleteArticle(Article article, User user);
}
