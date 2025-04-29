package fr.tp.eni.encheresskyjo.dal;

import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Category;
import fr.tp.eni.encheresskyjo.bo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class testArticleDAO {

    @Autowired
    private ArticleDAO articleDAO;

    @Test
    public void insertArticle() {
        Article article = new Article();
        article.setArticleName("Ordinateur portable");
        article.setDescription("Lenovo i7");
        article.setBidStartDate(LocalDate.now());
        article.setBidEndDate(LocalDate.now().plusDays(7));
        article.setStartingPrice(100);
        article.setSellingPrice(150);

        User user = new User();
        user.setUserId(1);
        article.setSeller(user);

        Category category = new Category();
        category.setCategoryId(2);
        article.setCategory(category);

        // Insertion
        articleDAO.create(article);
        System.out.println(article);

    }

    @Test
    public void selectArticleById() {

    }
}
