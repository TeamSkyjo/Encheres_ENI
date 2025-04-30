package fr.tp.eni.encheresskyjo.dal;

import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Category;
import fr.tp.eni.encheresskyjo.bo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class TestArticleDAO {

    @Autowired
    private ArticleDAO articleDAO;

    @Test
    public void test_create() {
        Article article = new Article();
        article.setArticleName("Ordinateur portable");
        article.setDescription("Lenovo i15");
        article.setStartDate(LocalDate.now());
        article.setEndDate(LocalDate.now().plusDays(7));
        article.setStartingPrice(1300);
        article.setSellingPrice(1300);

        User user = new User();
        user.setUserId(1);
        article.setSeller(user);

        Category category = new Category();
        category.setCategoryId(1);
        article.setCategory(category);

        // Insertion
        articleDAO.create(article);
        System.out.println(article);

    }

    @Test
    public void test_readById() {
        int articleId = 1;
        // Ordi portable Lenovo
        System.out.println(articleDAO.readByID(articleId));

    }

    @Test
    public void test_readAll() {
        List<Article> articles = articleDAO.readAll();

        for (Article article : articles) {
            System.out.println(article);
        }
    }

    @Test
    public void test_readByName() {
        articleDAO.readByName("Ordinateur").forEach(System.out::println);

    }

    @Test
    public void test_readByCategory() {
        System.out.println(articleDAO.readByCategory("Ameublement"));

    }

    @Test
    public void test_update() {
        Article article = articleDAO.readByID(1);
        System.out.println("Avant update : " + article);

        article.setArticleName("nouveau nom");
        article.setImageUrl("ours_url");
        article.setSellingPrice(780);

        articleDAO.update(article);

        System.out.println("Après update : " + article);
    }


    @Test
    public void test_delete() {
        articleDAO.delete(7);
        articleDAO.readAll().forEach(System.out::println);
    }

    @Test
    public void test_isArticleUnique() {
        Article article = new Article();
        article.setArticleName("Ordinateur portable");
        article.setDescription("Lenovo i15");
        article.setStartDate(LocalDate.now());
        article.setEndDate(LocalDate.now().plusDays(7));
        article.setStartingPrice(1300);
        article.setSellingPrice(1300);

        User user = new User();
        user.setUserId(1);
        article.setSeller(user);

        Category category = new Category();
        category.setCategoryId(1);
        article.setCategory(category);

        System.out.println(articleDAO.isArticleUnique(article));
    }
}
