package fr.tp.eni.encheresskyjo.bll.impl;

import fr.tp.eni.encheresskyjo.bll.ArticleService;
import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.dal.*;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author Teamskyjo
 * @Version 1.0
 * Class to secure the connection between the data & user for the Articles.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    //Dependencies Injection
    private ArticleDAO articleDAO;
    private BidDAO bidDAO;
    private UserDAO userDAO;
    private CategoryDAO categoryDAO;
    private PickupDAO pickupDAO;

    public ArticleServiceImpl(ArticleDAO articleDAO, BidDAO bidDAO, UserDAO userDAO, CategoryDAO categoryDAO, PickupDAO pickupDAO) {
        this.articleDAO = articleDAO;
        this.bidDAO = bidDAO;
        this.userDAO = userDAO;
        this.categoryDAO = categoryDAO;
        this.pickupDAO = pickupDAO;
    }

    @Override
    public void saveArticle(Article article) {

    }

    @Override
    public List<Article> getArticles() {




        return List.of();
    }

    @Override
    public List<Article> GetFilteredArticles(Article article) {
        return List.of();
    }
}
