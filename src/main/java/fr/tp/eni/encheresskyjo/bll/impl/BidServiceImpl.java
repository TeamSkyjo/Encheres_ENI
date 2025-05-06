package fr.tp.eni.encheresskyjo.bll.impl;

import fr.tp.eni.encheresskyjo.bll.BidService;
import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Bid;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dal.*;
import fr.tp.eni.encheresskyjo.exception.BusinessCode;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
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
    public void createBid(User user, Article article, int bidPrice) {
        BusinessException businessException = new BusinessException();
        boolean isValid = true ;

        isValid = isUserValid(user, businessException);
        isValid &= isArticleValid(article, businessException);
        isValid &= isBidPriceValid(bidPrice, article, businessException);

        if (isValid) {
            //TODO : @Transactionnal ?
            //TODO : Récupérer l'ancien utilisateur
            // récupérer le montant de son enchère
            // recréditer le montant sur son compte

            //TODO: enlever le montant du bid à l'utilisateur en cours
            // mettre à jour le sellingprice dans l'article
            // Créer le bid !

        }
        else {
            throw businessException;
        }
    }

    private boolean isUserValid (User user, BusinessException businessException) {
        boolean isValid = true ;
        if (userDAO.readById((user.getUserId())) == null) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_BID_USER_NULL);
        }
        return isValid;
    }

    private boolean isArticleValid (Article article, BusinessException businessException) {
        boolean isValid = true ;
        if (articleDAO.readByID((article.getArticleId())) == null) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_BID_ARTICLE_NULL);
        }
        return isValid;
    }

    private boolean isBidPriceValid (int bidPrice, Article article, BusinessException businessException) {
        boolean isValid = true ;
        Bid bestBid = getBestBid(article);
        if (bestBid.getBidPrice() > bidPrice) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_BID_PRICE_LOWER_BEST_BID);
        }
        if (article.getStartingPrice() > bidPrice) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_BID_PRICE_LOWER_STARTING_PRICE);
        }
        return isValid;
    }

    @Override
    public Bid getBestBid(Article article) {
        List<Bid> bids = bidDAO.readByArticle(article.getArticleId());
        Bid maxBid = bids.stream()
                .max(Comparator.comparingInt(Bid::getBidPrice))
                .orElse(null);
        return maxBid;
    }

    @Override
    public List<Bid> getBidsByUser(int userId) {
        return List.of();
    }

    @Override
    public List<Bid> getBidsWonByUser(int userId) {
        return List.of();
    }
}
