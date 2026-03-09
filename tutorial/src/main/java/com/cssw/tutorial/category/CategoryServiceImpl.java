package com.cssw.tutorial.category;

import com.cssw.tutorial.category.model.Category;
import com.cssw.tutorial.category.model.CategoryDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return (List<Category>) this.categoryRepository.findAll();
    }

    @Override
    public void save(Long id, CategoryDTO dto) {
        Category category;
        if (id == null) {
            category = new Category();
        } else {
            category = this.categoryRepository.findById(id).orElse(null);
        }
        category.setName(dto.getName());
        this.categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (this.categoryRepository.findById(id).orElse(null) == null) {
            throw new Exception("Not exists");
        }
        this.categoryRepository.deleteById(id);
    }
}
