// CategoryService.java
package com.aerb.docs.service;

import com.aerb.docs.entity.Category;
import com.aerb.docs.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllRootCategoriesWithChildren() {
        List<Category> rootCategories = categoryRepository.findByParentIsNull();
        // Lazy loading, so subcategories will load automatically on serialization with Jackson
        return rootCategories;
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
