package fr.tp.eni.encheresskyjo.dal;

import fr.tp.eni.encheresskyjo.bo.Article;

import java.util.List;


public interface ArticleDAO {

    void create(Article article);

    // pas nécessaire
    void update(int articleId, Article article);

    Article read(int articleId);
    List<Article> readAll();

    void delete();
    void updateSellingPrice();
    void readSalesInProgress();
    void readWaitingSales();
    void readEndedSales();
    void addImage();
    void readByCategory();
}
