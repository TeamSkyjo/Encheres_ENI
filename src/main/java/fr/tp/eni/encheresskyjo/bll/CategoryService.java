package fr.tp.eni.encheresskyjo.bll;

import fr.tp.eni.encheresskyjo.bo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(int categoryId);
    void createCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(int categoryId);
}
