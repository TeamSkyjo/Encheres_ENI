package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Bid;

public interface BidService {
    void createBid(Bid bid);
    Bid gestBestBid(Article article);
}
