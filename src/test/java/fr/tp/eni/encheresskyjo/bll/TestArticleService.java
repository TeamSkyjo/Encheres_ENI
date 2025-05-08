package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.*;
import fr.tp.eni.encheresskyjo.dal.UserDAO;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class TestArticleService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserDAO userDAO;

    @Test
    public void test_createArticle() {
        Article article = new Article();
        article.setArticleName("Chaise gaming");
        article.setDescription("Chaise ergonomique confortable pour de longues heures de jeu.");
        article.setStartDate(LocalDate.now().plusDays(1));
        article.setEndDate(LocalDate.now().plusDays(7));
        article.setStartingPrice(100);
        article.setImageUrl("https://example.com/chaise.jpg");

        Category category = new Category();
        category.setCategoryId(1);
        article.setCategory(category);

        article.setSeller(userDAO.readById(2));

        Pickup pickup = new Pickup();
        pickup.setCity(article.getSeller().getCity());
        pickup.setZip(article.getSeller().getZip());
        pickup.setStreet(article.getSeller().getStreet());
        article.setPickup(pickup);

        try {
            articleService.createArticle(article);
            System.out.println("Article créé avec succès");
        } catch (BusinessException businessException) {
            System.out.println(businessException.getKeys());
        }
    }

    @Test
    public void test_updateArticle() {
        Article article = articleService.getArticleById(1);
        article.setDescription("Dell XY12345, processeur Intel Core i5");
        article.getPickup().setCity("Bliblablou");

        try {
            articleService.updateArticle(article);
            System.out.println("Article mis à jour avec succès");
        } catch (BusinessException businessException) {
            System.out.println(businessException.getKeys());
        }
    }

    @Test
    public void test_getArticles() {
        List<Article> articles = articleService.getArticles();
        System.out.println("Articles récupérés : " + articles.size());
        articles.forEach(System.out::println);
    }

    @Test
    public void test_getByStatus() {
        List<Article> articles = articleService.getByStatus(ArticleStatus.ONGOING);
        System.out.println("Articles en cours : " + articles.size());
        articles.forEach(System.out::println);
    }

    @Test
    public void test_getFilteredArticles() {
        String pattern = "o";
        Category category = new Category();
        category.setCategoryId(1);

        // With both filters
        List<Article> articles = articleService.getFilteredArticles(pattern, category);
        System.out.println("Articles filtrés par pattern ET category : " + articles.size());
        articles.forEach(System.out::println);

        // No filters
        articles = articleService.getFilteredArticles(null, null);
        System.out.println("Articles sans filtres : " + articles.size());
        articles.forEach(System.out::println);

        // Filtered by category only
        articles = articleService.getFilteredArticles(null, category);
        System.out.println("Articles filtrés par category : " + articles.size());
        articles.forEach(System.out::println);

        // Filtered by pattern only
        articles = articleService.getFilteredArticles(pattern, null);
        System.out.println("Articles filtrés par pattern : " + articles.size());
        articles.forEach(System.out::println);
    }

    @Test
    public void test_deleteArticle(){
        Article article = articleService.getArticleById(14);
        User user = userDAO.readById(1);
        articleService.deleteArticle(article, user);

    }
}

