package com.cssw.tutorial.category;

import com.cssw.tutorial.category.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
