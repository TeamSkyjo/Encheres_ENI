package fr.tp.eni.encheresskyjo.dal;

import fr.tp.eni.encheresskyjo.bo.Article;

import java.util.List;


public interface ArticleDAO {

    void create(Article article);

    Article readByID(int articleId);
    List<Article> readAll();

    List<Article> readByName(String pattern);
    List<Article> readByCategory(String libelle);

    void update(Article article);

    void delete(int articleId);

    boolean isArticleUnique(Article article);
}
