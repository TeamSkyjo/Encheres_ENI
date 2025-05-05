package fr.tp.eni.encheresskyjo.converter;

import fr.tp.eni.encheresskyjo.bll.CategoryService;
import fr.tp.eni.encheresskyjo.bo.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoryConverter implements Converter<String, Category> {

    private CategoryService categoryService;

    public StringToCategoryConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Category convert(String categoryId) {
        Category category = categoryService.getCategoryById(Integer.parseInt(categoryId));
        return category;
    }
}
