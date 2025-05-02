package fr.tp.eni.encheresskyjo.bll.impl;

import fr.tp.eni.encheresskyjo.bll.BidService;
import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Bid;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dal.*;

public class BidServiceImpl implements BidService {

    //Dependencies Injection
    private ArticleDAO articleDAO;
    private BidDAO bidDAO;
    private UserDAO userDAO;
    private CategoryDAO categoryDAO;
    private PickupDAO pickupDAO;

    public BidServiceImpl(ArticleDAO articleDAO, BidDAO bidDAO, UserDAO userDAO, CategoryDAO categoryDAO, PickupDAO pickupDAO) {
        this.articleDAO = articleDAO;
        this.bidDAO = bidDAO;
        this.userDAO = userDAO;
        this.categoryDAO = categoryDAO;
        this.pickupDAO = pickupDAO;
    }

    /**
     * Private method to centralize the link between a bid, its user and the article
     * @param bid
     */
    private void linkUserAndArticleToBid(Bid bid) {
        User buyer = userDAO.readById(bid.getBuyer().getUserId());
        bid.setBuyer(buyer);
        Article article = articleDAO.readByID((bid.getArticle().getArticleId()));
        bid.setArticle(article);
    }
    @Override
    public void createBid(Bid bid) {

    }

    @Override
    public Bid gestBestBid(Article article) {
        return null;
    }
}
