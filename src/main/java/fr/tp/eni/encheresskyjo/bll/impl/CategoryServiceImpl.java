package fr.tp.eni.encheresskyjo.bll.impl;

import fr.tp.eni.encheresskyjo.bll.CategoryService;
import fr.tp.eni.encheresskyjo.bo.Category;
import fr.tp.eni.encheresskyjo.dal.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    //Dependencies Injection
    private ArticleDAO articleDAO;
    private BidDAO bidDAO;
    private UserDAO userDAO;
    private CategoryDAO categoryDAO;
    private PickupDAO pickupDAO;

    public CategoryServiceImpl(ArticleDAO articleDAO, BidDAO bidDAO, UserDAO userDAO, CategoryDAO categoryDAO, PickupDAO pickupDAO) {
        this.articleDAO = articleDAO;
        this.bidDAO = bidDAO;
        this.userDAO = userDAO;
        this.categoryDAO = categoryDAO;
        this.pickupDAO = pickupDAO;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.readAll();
    }

    @Override
    public Category getCategoryById(int categoryId) {
        return categoryDAO.read(categoryId);
    }

    @Override
    public void createCategory(Category category) {
        //TODO : validate category label
    }

    @Override
    public void updateCategory(Category category) {
        //TODO : validate category label
    }

    @Override
    public void deleteCategory(int categoryId) {
        //TODO : DELETE works with a non existant ID
    }
}
