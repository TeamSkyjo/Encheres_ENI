package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Bid;
import java.util.List;

public interface BidService {
    void createBid(Bid bid);
    Bid getBestBid(Article article);
    List<Bid> getBidsByUser(int userId);
    List<Bid> getBidsWonByUser(int userId);
}
