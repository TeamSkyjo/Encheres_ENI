package fr.tp.eni.encheresskyjo.bll.impl;

import fr.tp.eni.encheresskyjo.bll.ArticleService;
import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Category;
import fr.tp.eni.encheresskyjo.bo.Pickup;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dal.*;
import fr.tp.eni.encheresskyjo.exception.BusinessCode;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    @Transactional
    public void saveArticle(Article article) {
        boolean isValid = true;
        BusinessException businessException = new BusinessException();

        isValid = isNameValid(article.getArticleName(), businessException);
        isValid &= isDescriptionValid(article.getDescription(), businessException);
        isValid &= areDatesValid(article.getStartDate(), article.getEndDate(), businessException);
        isValid &= isStartingPriceValid(article.getStartingPrice(), businessException);
        isValid &= isImageUrlValid(article.getImageUrl(), businessException);
        isValid &= isCategoryValid(article.getCategory(), businessException);
        isValid &= isPickupValid(article.getPickup(), businessException);
        // Méthode à mettre en fin pour des raisons de sécurité
        // Il y a un appel en BDD donc on vérifie les champs avant
        isValid &= isArticleValid(article, businessException);

        if (isValid) {
            this.articleDAO.create(article);
            this.pickupDAO.create(article.getPickup());
        } else {
            businessException.addKey(BusinessCode.VALID_ARTICLE);
            throw businessException;
        }
    }

    @Override
    public List<Article> getArticles() {

        return List.of();
    }

    @Override
    public List<Article> GetFilteredArticles(Article article) {
        return List.of();
    }

    private boolean isNameValid(String articleName, BusinessException businessException) {
        boolean isValid = true;

        if (articleName == null || articleName.isBlank()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ARTICLE_NAME_BLANK);
        } else {
            if (articleName.length() > 30) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_ARTICLE_NAME_LENGTH_MAX);
            }
            if (articleName.length() < 3) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_ARTICLE_NAME_LENGTH_MIN);
            }
        }

        return isValid;
    }

    private boolean isDescriptionValid(String description, BusinessException businessException) {
        boolean isValid = true;

        if (description == null || description.isBlank()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ARTICLE_DESCRIPTION_NAME_BLANK);
        } else {
            if (description.length() > 300) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_ARTICLE_DESCRIPTION_NAME_LENGTH_MAX);
            }
            if (description.length() < 10) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_ARTICLE_DESCRIPTION_NAME_LENGTH_MIN);
            }
        }

        return isValid;
    }

    private boolean areDatesValid(LocalDate startDate, LocalDate endDate, BusinessException businessException) {
        boolean isValid = true;

        if(startDate == null) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ARTICLE_STARTDATE_NULL);
        } else {
            if(startDate.isBefore(LocalDate.now())) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_ARTICLE_STARTDATE_BEFORE);
            }
            if(endDate == null) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_ARTICLE_ENDDATE_NULL);
            } else {
                if(endDate.isBefore(LocalDate.now()) || endDate.isBefore(startDate)) {
                    isValid = false;
                    businessException.addKey(BusinessCode.VALID_ARTICLE_ENDDATE_BEFORE);
                }
            }
        }

        return isValid;
    }
}
