package fr.tp.eni.encheresskyjo.bll.impl;

import fr.tp.eni.encheresskyjo.bll.BidService;
import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.ArticleStatus;
import fr.tp.eni.encheresskyjo.bo.Bid;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dal.*;
import fr.tp.eni.encheresskyjo.exception.BusinessCode;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
     * This methods allow the creation of a new bid for an article.
     * First : it checks whether the bid, user, article is valid. if so :
     * Second : it recovers the last bid to get the credit back to the former best-bider.
     * Third : it creates the new bid and update the credit of the ongoing bider.
     * @param user
     * @param article
     * @param bidPrice
     */
    @Transactional
    @Override
    public void createBid(User user, Article article, int bidPrice) {
        BusinessException businessException = new BusinessException();
        boolean isValid = true ;
        LocalDate now = LocalDate.now();

        isValid = isUserValid(user, businessException);
        isValid &= isArticleValid(article, businessException);
        isValid &= isBidPriceValid(bidPrice, article, businessException);
        isValid &= isDateValid(article, now, businessException);

        if (isValid) {
            //Give back its credits to the last best bider.
            creditLastBuyer(article);
            //collect credits of the actual user
            int userCreditUpdated = user.getCredit()-bidPrice;
            user.setCredit(userCreditUpdated);
            userDAO.updateCredit(user.getUserId(), userCreditUpdated);
            //Create new bid - depending if the buyer has already tried to buy this article.
            Bid newBid = new Bid(now, bidPrice, user, article);
            List<Bid> userBids= bidDAO.readByUser(user.getUserId());
            Bid lastBid = userBids.stream()
                            .filter(b -> b.getArticle().equals(article))
                            .filter(b ->b.getBuyer().equals(user))
                            .findFirst()
                            .orElse(null);
            if (lastBid != null) {
                bidDAO.update(newBid);
            }
            else {
                bidDAO.create(newBid);
            }
        }
        else {
            throw businessException;
        }
    }

    /**
     * Private methode to give back credit to the last buyer in case a new bid is made.
     * @param article
     */
    private void creditLastBuyer(Article article) {
        Bid lastBid = getBestBid(article);
        linkUserAndArticleToBid(lastBid);
        User lastBuyer = lastBid.getBuyer();
        int lastBidPrice = lastBid.getBidPrice();
        int lastCredit = lastBuyer.getCredit();
        int newCredit = lastCredit + lastBidPrice;
        lastBuyer.setCredit(newCredit);
        userDAO.updateCredit(lastBuyer.getUserId(), newCredit);
    }

    /**
     * Private method to centralize the link between a bid, its user and the article
     * @param bid
     */
    void linkUserAndArticleToBid(Bid bid) {
        User buyer = userDAO.readById(bid.getBuyer().getUserId());
        bid.setBuyer(buyer);
        Article article = articleDAO.readByID((bid.getArticle().getArticleId()));
        bid.setArticle(article);
    }

    private boolean isDateValid(Article article, LocalDate now, BusinessException businessException) {
        boolean isValid = true ;
        if (article.getStartDate().isAfter(now)) {
            isValid = false ;
            businessException.addKey(BusinessCode.VALID_BID_DATE_BEFORE_START);
        }
        if (article.getEndDate().isBefore(now)) {
            isValid = false ;
            businessException.addKey(BusinessCode.VALID_BID_DATE_AFTER_END);
        }
        return isValid;
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

    /**
     * end date, insert selling price, credit seller
     * and check buyer uncredit ?
     *
     * @param article
     */
    @Override
    public void closeBid(Article article) {

        ArticleStatus status = article.readStatus();
        System.out.println("Statut : " +status);


        if (article.getSellingPrice() == 0 && status == ArticleStatus.ENDED) {
            Bid bestBid = getBestBid(article);
            System.out.println("\nBest bid : " + bestBid);


            // update Article selling price
            int bestPrice = bestBid.getBidPrice();
            article.setSellingPrice(bestPrice);
            articleDAO.update(article);
            System.out.println("\nupdated article : " + article);

            // update seller credit
            User seller = article.getSeller();
            System.out.println("\n Seller credit before : " + seller.getCredit());
            seller.setCredit(seller.getCredit() + bestPrice);
            userDAO.updateCredit(seller.getUserId(), seller.getCredit());
            System.out.println("\n Seller credit after : " + seller.getCredit());

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
    public List<Article> getBidsByUser(int userId) {
        List<Bid> userBids = bidDAO.readByUser(userId);
        List<Article> articles = new ArrayList<>();
        for (Bid bid : userBids) {
            Article article = articleDAO.readByID(bid.getArticle().getArticleId());
            articles.add(article);
        }
        return articles;
    }

    @Override
    public List<Article> getBidsWonByUser(int userId) {
        //TODO: as soon as the closure of bid is done.
        // Recover the list of the bids for the user
        // Recover the status of each these bids.
        // For those who are closed, check whether the sellingprice = bidingprice of current user.
        // loop to recover each article for each bid ? Depend if we want to load the biding price.
        return List.of();
    }
}
