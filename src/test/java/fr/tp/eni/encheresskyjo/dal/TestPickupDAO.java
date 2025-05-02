package fr.tp.eni.encheresskyjo.dal;

import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Pickup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author TeamSkyjo
 * @Version 1.0
 * Class to test all the request for Pickup in the database.
 * For Version 1.0 : .
 */

@SpringBootTest
public class TestPickupDAO {

    @Autowired
    PickupDAO pickupDAO;

    /**
     * Test to check the insertion of data into the table "RETRAIT".
     * Warning : This table is linked to the ARTICLES table.
     * To test a valid test, you must add a row into ARTICLES with the no_article you want to add.
     * Otherwise you will have a conflict with the foreign key.
     */
    @Test
    public void test_create() {
        Article article = new Article();
        article.setArticleId(5);
        Pickup pickup = new Pickup("3 rue des mouettes", "29000", "Quimper");
        pickupDAO.create(article.getArticleId(), pickup);
    }

    @Test
    public void test_update() {
        Article article = new Article();
        article.setArticleId(4);
        Pickup pickup = new Pickup("3 rue des mouettes", "29000", "Quimper");
        pickupDAO.update(article.getArticleId(), pickup);
    }

    @Test
    public void test_read() {
        Pickup pickup = pickupDAO.read(5);
        System.out.println(pickup);
    }
}
