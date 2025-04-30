package fr.tp.eni.encheresskyjo.bll.impl;

import fr.tp.eni.encheresskyjo.bll.ArticleService;
import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Category;
import fr.tp.eni.encheresskyjo.dal.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
        List<Article> articles = articleDAO.readAll();
        return articles;
    }

    @Override
    public List<Article> GetFilteredArticles(String pattern, Category category)
            //throws BusinessException
    {
        //BusinessException businessException = new BusinessException();
        List<Article> filteredArticles = new ArrayList<>();
        if (category == null) {
            if (pattern == null || pattern.isEmpty()) {
                filteredArticles = articleDAO.readAll();
            }
            else {
                filteredArticles = articleDAO.readByName(pattern);
            }
        }
        else {
            boolean isValid = true ;
            isValid = isCategoryValid(category
                    //,
                    //businessException
                    );

            if (pattern == null || pattern.isEmpty()) {
                filteredArticles = articleDAO.readByCategory(category.getLabel());
            }
            else {
                filteredArticles = articleDAO.readByName(pattern).stream()
                        .filter(article -> article.getCategory().equals(category))
                        .toList();
            }
        }
        return filteredArticles;
    }

    private boolean isCategoryValid(Category category
                                    //,
                                    //BusinessException businessException
    ) {
        boolean isValid = true;
        //Category == null not useful for GetFilteredArticles
        if (category == null) {
            isValid = false;
            //BusinessException.addMessage(BusinessCode.CATEGORY_NULL);
        }
        else {
            try {
                categoryDAO.read(category.getCategoryId());
            } catch ( RuntimeException e) {
                isValid = false;
                //BusinessException.addMessage(BusinessCode.CATEGORY_UNKNOWN_ID);
            }
        }
        return isValid;
    }
}
