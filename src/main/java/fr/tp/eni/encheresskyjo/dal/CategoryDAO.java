package fr.tp.eni.encheresskyjo.dal;

import fr.tp.eni.encheresskyjo.bo.Category;

import java.util.List;

public interface CategoryDAO {
    void create(Category category);
    List<Category> readAll();
    Category read(int categoryId);
    void delete(int categoryId);
    void update(Category category);
}
