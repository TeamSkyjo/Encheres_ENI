package fr.tp.eni.encheresskyjo.dal;

import fr.tp.eni.encheresskyjo.bo.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestCategoryDAO {

    @Autowired
    private CategoryDAO categoryDAO;

    @Test
    public void test_readAll() {
        List<Category> categories = categoryDAO.readAll();
        System.out.println(categories);
    }

    @Test
    public void test_read() {
        // Should be "Sport&Loisirs"
        Category category = categoryDAO.read(3);
        System.out.println(category);
    }

    @Test
    public void test_create() {
        Category category = new Category();
        category.setLabel("Chaussures");
        categoryDAO.create(category);
        System.out.println(category);
    }

    @Test
    public void test_update() {
        Category category = new Category();
        category = categoryDAO.read(10);
        category.setLabel("Blablabla");
        categoryDAO.update(category);
        System.out.println(category);
    }

    @Test
    public void test_delete() {
        categoryDAO.delete(9);
    }
}
