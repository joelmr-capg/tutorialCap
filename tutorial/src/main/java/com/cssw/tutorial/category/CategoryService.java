package com.cssw.tutorial.category;

import com.cssw.tutorial.category.model.Category;
import com.cssw.tutorial.category.model.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    void save(Long id, CategoryDTO dto);

    void delete(Long id) throws Exception;
}
