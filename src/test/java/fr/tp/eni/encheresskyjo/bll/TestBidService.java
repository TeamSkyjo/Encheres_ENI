package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.*;
import fr.tp.eni.encheresskyjo.dal.BidDAO;
import fr.tp.eni.encheresskyjo.dal.UserDAO;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class TestBidService {


    @Autowired
    private BidService bidService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    @Test
    public void test_getBestBid() {
        Article article = articleService.getArticleById(4);
        System.out.println(article);

        Bid bid = bidService.getBestBid(article);
        System.out.println(bid);
    }

    @Test
    public void test_createBid() {
        Article article = articleService.getArticleById(2);
        User user = userService.getByUsername("meublequeen");
        bidService.createBid(user, article, 560);
    }

    @Test
    public void test_bidClosure() {
        Article article = articleService.getArticleById(4);
        Bid bestBid = bidService.getBestBid(article);
        System.out.println(bestBid);

        bidService.closeBid(article);
        System.out.println(article);

    }

    @Test
    public void test_bidClosure_fail() {
        // Vélo de course / seller: 3 (sporty) / fin enchères 2025-05-05
        Article article = articleService.getArticleById(3);
        Bid bestBid = bidService.getBestBid(article);
        System.out.println(bestBid);

        try {
            bidService.closeBid(article);
            System.out.println(article);
        } catch(BusinessException e) {
            System.out.println("BusinessException : " + e.getKeys());
        }

    }

//    //Test to verify that the method linkUserAndArticletoBid works properly. The method was modified from private to public for the test.
//    @Test
//    public void test_linkUserAndArticleToBid() {
//        Article article = articleService.getArticleById(4);
//        Bid bid = bidService.getBestBid(article);
//        bidService.linkUserAndArticleToBid(bid);
//        System.out.println(bid);
//    }
}
