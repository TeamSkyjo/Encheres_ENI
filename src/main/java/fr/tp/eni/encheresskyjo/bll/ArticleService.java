package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Category;

import java.util.List;

public interface ArticleService {
    void createArticle(Article article);
    List<Article> getArticles();
    List<Article> getFilteredArticles(String pattern, Category category);
}
