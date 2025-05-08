package fr.tp.eni.encheresskyjo.dal;

import fr.tp.eni.encheresskyjo.bo.Bid;
import java.util.List;

public interface BidDAO {
    void create(Bid bid);
    List<Bid> readAll();
    List<Bid> readByArticle(int articleId);
    List<Bid> readByUser(int userId);
    void delete(Bid bid);
    void update(Bid bid);
}
