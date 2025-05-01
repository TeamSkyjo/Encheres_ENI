package fr.tp.eni.encheresskyjo.bll.impl;

import fr.tp.eni.encheresskyjo.bll.ArticleService;
import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Category;
import fr.tp.eni.encheresskyjo.bo.Pickup;
import fr.tp.eni.encheresskyjo.dal.*;
import fr.tp.eni.encheresskyjo.exception.BusinessCode;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author Teamskyjo
 * @Version 1.0
 * Class to secure the connection between the data & user for the Articles.
 */
// TODO : test all three methods in TestArticleService
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
    public void createArticle(Article article) {

        BusinessException businessException = new BusinessException();
        boolean isValid = validateArticle(article, businessException);

        if (isValid && isArticleUnique(article, businessException)) {
            this.articleDAO.create(article);
        } else {
            if (!isValid) {
                businessException.addKey(BusinessCode.VALID_ARTICLE);
            }
            if (!isArticleUnique(article, businessException)) {
                businessException.addKey(BusinessCode.VALID_ARTICLE_UNIQUE);
            }
            throw businessException;
        }
    }

    @Override
    public void updateArticle(Article article) {

        BusinessException businessException = new BusinessException();
        boolean isValid = validateArticle(article, businessException);

        if (isValid) {
            this.articleDAO.update(article);
        } else {
            businessException.addKey(BusinessCode.VALID_ARTICLE);
            throw businessException;
        }
    }

    private boolean validateArticle(Article article, BusinessException businessException) {
        boolean isValid = true;

        isValid &= isNameValid(article.getArticleName(), businessException);
        isValid &= isDescriptionValid(article.getDescription(), businessException);
        isValid &= areDatesValid(article.getStartDate(), article.getEndDate(), businessException);
        isValid &= isStartingPriceValid(article.getStartingPrice(), businessException);
        isValid &= isImageUrlValid(article.getImageUrl(), businessException);
        isValid &= isCategoryValid(article.getCategory(), businessException);
        isValid &= isPickupValid(article.getPickup(), businessException);

        return isValid;
    }

    @Override
    public List<Article> getArticles() {
        List<Article> articles = articleDAO.readAll();
        return articles;
    }

    @Override
    public List<Article> getFilteredArticles(String pattern, Category category) {
        BusinessException businessException = new BusinessException();
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
            if(isCategoryValid(category, businessException)) {
                if (pattern == null || pattern.isEmpty()) {
                    filteredArticles = articleDAO.readByCategory(category.getCategoryId());
                } else {
                    filteredArticles = articleDAO.readByName(pattern).stream()
                            .filter(article -> article.getCategory().equals(category))
                            .toList();
                }
            } else {
                throw businessException;
            }
        }
        return filteredArticles;
    }

    private boolean isCategoryValid(Category category, BusinessException businessException) {
        boolean isValid = true;
        //Category == null not useful for GetFilteredArticles
        if (category == null) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ARTICLE_CATEGORY_NULL);
        }
        else {
            try {
                categoryDAO.read(category.getCategoryId());
            } catch ( RuntimeException e) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_ARTICLE_CATEGORY_UNKNOWN_ID);
            }
        }
        return isValid;
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

    private boolean isStartingPriceValid(int startingPrice, BusinessException businessException) {
        boolean isValid = true;

        if (startingPrice < 1) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ARTICLE_STARTINGPRICE_MIN);
        }

        return isValid;
    }

    private boolean isImageUrlValid(String imageURL, BusinessException businessException) {
        boolean isValid = true;

        String URLValidationRegex = "/https?:\\/\\/[^\\s\"]+\\.(?:jpe?g|png|gif)(?:\\?[^\\s\"]*)?\n/gm";

        if (imageURL.isEmpty() && !imageURL.matches(URLValidationRegex)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ARTICLE_IMAGEURL_PATTERN);
        }

        return isValid;
    }

    private boolean isPickupValid(Pickup pickup, BusinessException businessException) {
        boolean isValid = true;

        if (pickup == null) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ARTICLE_PICKUP_NULL);
        }

        return isValid;
    }

    private boolean isArticleUnique(Article article, BusinessException businessException) {
        boolean isValid = articleDAO.isArticleUnique(article);

        if (!isValid) {
            businessException.addKey(BusinessCode.VALID_ARTICLE_UNIQUE);
        }

        return isValid;
    }
}
