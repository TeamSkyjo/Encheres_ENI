package fr.tp.eni.encheresskyjo.dal;

import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Bid;
import fr.tp.eni.encheresskyjo.bo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class TestBidDAO {

    @Autowired
    private BidDAO bidDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ArticleDAO articleDAO;

    @Test
    public void test_create() {
        Bid bid = new Bid();
        bid.setBidPrice(350);
        bid.setBidDate(LocalDate.now());

        bid.setBuyer(userDAO.readById(2));
        bid.setArticle(articleDAO.readByID(2));

        bidDAO.create(bid);
        System.out.println(bid);
    }

    @Test
    public void test_readAll() {
        List<Bid> bids = bidDAO.readAll();
        for (Bid bid : bids) {
            System.out.println(bid);
        }
    }

    @Test
    public void test_readByArticle() {
        List<Bid> bids = bidDAO.readByArticle(3);
        for (Bid bid : bids) {
            System.out.println(bid);
        }
    }

    @Test
    public void test_readByUser() {
        List<Bid> bids = bidDAO.readByUser(3);
        for (Bid bid : bids) {
            System.out.println(bid);
        }
    }

    @Test
    public void test_delete() {
        Bid bid = new Bid();
        bid.setBidPrice(350);
        bid.setBidDate(LocalDate.now());

        bid.setBuyer(userDAO.readById(2));
        bid.setArticle(articleDAO.readByID(2));

        bidDAO.delete(bid);
    }
}
