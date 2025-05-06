package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Bid;
import fr.tp.eni.encheresskyjo.bo.User;

import java.util.List;

public interface BidService {
    void createBid(User user, Article article, int bidPrice);
    void closeBid(Article article);
    Bid getBestBid(Article article);
    List<Bid> getBidsByUser(int userId);
    List<Bid> getBidsWonByUser(int userId);
}
