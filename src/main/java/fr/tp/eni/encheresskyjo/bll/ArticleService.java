package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.Article;

import java.util.List;

public interface ArticleService {
    void saveArticle(Article article);
    List<Article> getArticles();
    List<Article> GetFilteredArticles(Article article);
}
