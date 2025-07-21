package com.aerb.budget.controller;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aerb.docs.entity.Category;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @GetMapping("/categories")
    public List<Category> getCategories() {
        // Replace this with DB logic if needed
        Category doc1 = new Category(5L, "DocType 1", null);
        Category doc2 = new Category(6L, "DocType 2", null);

        Category section = new Category(4L, "Section A1", List.of(doc1, doc2));
        Category subdivision = new Category(2L, "Subdivision A", List.of(section));
        Category division = new Category(1L, "Division A", List.of(subdivision));

        return List.of(division);
    }
}