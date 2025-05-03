package fr.tp.eni.encheresskyjo.bll.impl;

import fr.tp.eni.encheresskyjo.bll.ArticleService;
import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.ArticleStatus;
import fr.tp.eni.encheresskyjo.bo.Category;
import fr.tp.eni.encheresskyjo.bo.Pickup;
import fr.tp.eni.encheresskyjo.dal.*;
import fr.tp.eni.encheresskyjo.exception.BusinessCode;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Teamskyjo
 * @version 1.0
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
    public void createArticle(Article article) {

        BusinessException businessException = new BusinessException();
        boolean isValid = validateArticle(article, businessException);

        if (!isValid) {
            businessException.addKey(BusinessCode.VALID_ARTICLE);
            throw businessException;
        } else {
            if(!isArticleUnique(article, businessException)) {
                businessException.addKey(BusinessCode.VALID_ARTICLE_UNIQUE);
                throw businessException;
            }
            this.articleDAO.create(article);
            // Create associated Pickup
            this.pickupDAO.create(article.getArticleId(), article.getPickup());
        }
    }

    @Override
    @Transactional
    public void updateArticle(Article article) {

        BusinessException businessException = new BusinessException();
        boolean isValid = validateArticle(article, businessException);
        isValid &= (article.readStatus() == ArticleStatus.NOT_STARTED);

        if (isValid) {
            this.articleDAO.update(article);
            this.pickupDAO.update(article.getArticleId(), article.getPickup());
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
    public Article getArticleById(int articleId) {
        Article article = articleDAO.readByID(articleId);
        if (article != null) {
            // Association with Pickup
            linkPickupToArticle(article);
        }
        return article;
    }

    @Override
    public List<Article> getByStatus(ArticleStatus articleStatus) {
        List<Article> articles = articleDAO.readAll();
        if (articles != null) {
            articles = articles.stream()
                    .filter(article -> article.readStatus() == articleStatus)
                            .collect(Collectors.toList());
            // Association with Pickup
            articles.forEach(article -> linkPickupToArticle(article));
        }
        return articles;
    }

    /**
     * Private method to centralize the link between an article and its pickup location
     * @param article
     */
    private void linkPickupToArticle(Article article) {
        Pickup pickup = pickupDAO.read(article.getArticleId());
        article.setPickup(pickup);
    }

    @Override
    public List<Article> getArticles() {
        List<Article> articles = articleDAO.readAll();
        if (articles != null) {
            // Association with Pickup
            articles.forEach(article -> linkPickupToArticle(article));
        }
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
        if (filteredArticles != null && !filteredArticles.isEmpty()) {
            // Association with Pickup
            filteredArticles.forEach(article -> linkPickupToArticle(article));
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
            } catch (RuntimeException e) {
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

        String URLValidationRegex = "(?i)^https?://[^\\s\"]+\\.(jpg|jpeg|png|gif)(\\?.*)?$";

        if (imageURL != null && !imageURL.matches(URLValidationRegex)) {
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
        } else {
            isValid &= isStreetValid(pickup.getStreet(), businessException);
            isValid &= isCityValid(pickup.getCity(), businessException);
            isValid &= isZipValid(pickup.getZip(), businessException);
        }

        return isValid;
    }

    private boolean isStreetValid(String street, BusinessException businessException) {
        boolean isValid = true;

        String streetValidationRegex = "^[0-9]{1,4}(?: ?[A-Za-z]{1,3})?\\s+[\\p{L}0-9 .,'-]+$";

        if (street == null || street.isBlank()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ADDRESS_STREET_NAME_BLANK);
        } else {
            if (street.length() > 30) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_ADDRESS_STREET_NAME_LENGTH_MAX);
            } else if (!street.matches(streetValidationRegex)) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_ADDRESS_STREET_NAME_FORMAT);
            }
        }

        return isValid;
    }

    private boolean isZipValid(String zip, BusinessException businessException) {
        boolean isValid = true;

        String postalCodeRegex = "^(0[1-9]|[1-8][0-9]|9[0-8])[0-9]{3}$";

        if (zip == null || zip.isBlank()) {
            businessException.addKey(BusinessCode.VALID_ADDRESS_ZIP_BLANK);
            return false;
        }

        if (zip.length() > 10) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ADDRESS_ZIP_LENGTH_MAX);
        } else if (!zip.matches(postalCodeRegex)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ADDRESS_ZIP_FORMAT);
        }

        return isValid;
    }

    private boolean isCityValid(String city, BusinessException businessException) {
        boolean isValid = true;

        if (city == null || city.isBlank()) {
            businessException.addKey(BusinessCode.VALID_ADDRESS_CITY_BLANK);
            return false;
        }

        if (city.length() > 30) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_ADDRESS_CITY_LENGTH_MAX);
        }

        return isValid;
    }

    private boolean isArticleUnique(Article article, BusinessException businessException) {

        return articleDAO.isArticleUnique(article);
    }
}
