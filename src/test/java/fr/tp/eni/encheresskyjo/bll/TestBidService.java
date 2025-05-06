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

//    //Test to verify that the method linkUserAndArticletoBid works properly. The method was modified from private to public for the test.
//    @Test
//    public void test_linkUserAndArticleToBid() {
//        Article article = articleService.getArticleById(4);
//        Bid bid = bidService.getBestBid(article);
//        bidService.linkUserAndArticleToBid(bid);
//        System.out.println(bid);
//    }
}
